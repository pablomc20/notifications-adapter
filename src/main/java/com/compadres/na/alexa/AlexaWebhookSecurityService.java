package com.compadres.na.alexa;

import com.amazon.ask.model.Application;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.servlet.verifiers.SkillRequestSignatureVerifier;
import com.amazon.ask.servlet.verifiers.SkillRequestTimestampVerifier;
import com.compadres.na.alexa.http.SimpleAlexaHttpRequest;
import com.compadres.na.config.AlexaProperties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AlexaWebhookSecurityService {

    private static final String HDR_SIGNATURE = "Signature";
    private static final String HDR_CERT_CHAIN = "SignatureCertChainUrl";

    private final AlexaProperties alexaProperties;
    private final SkillRequestSignatureVerifier signatureVerifier = new SkillRequestSignatureVerifier();
    private SkillRequestTimestampVerifier timestampVerifier;

    public AlexaWebhookSecurityService(AlexaProperties alexaProperties) {
        this.alexaProperties = alexaProperties;
    }

    @PostConstruct
    void initTimestampVerifier() {
        this.timestampVerifier = new SkillRequestTimestampVerifier(
                alexaProperties.getVerification().getTimestampToleranceSeconds(),
                TimeUnit.SECONDS);
    }

    /**
     * Validates Alexa signature, timestamp, and application id when verification is enabled.
     */
    public void verifyIfEnabled(RequestEnvelope envelope, byte[] rawBody, String signature, String certChainUrl) {
        if (!alexaProperties.getVerification().isEnabled()) {
            log.warn("Alexa request verification is DISABLED; do not use this configuration in production");
            return;
        }
        if (signature == null || signature.isBlank() || certChainUrl == null || certChainUrl.isBlank()) {
            throw new SecurityException("Missing Signature or SignatureCertChainUrl headers");
        }
        var alexaHttpRequest = new SimpleAlexaHttpRequest(signature, certChainUrl, rawBody, envelope);
        signatureVerifier.verify(alexaHttpRequest);
        timestampVerifier.verify(alexaHttpRequest);
        assertApplicationId(envelope);
    }

    private void assertApplicationId(RequestEnvelope envelope) {
        String expected = alexaProperties.getSkillId();
        if (expected == null || expected.isBlank()) {
            throw new IllegalStateException("alexa.skill-id must be configured when verification is enabled");
        }
        String actual = Optional.ofNullable(envelope.getContext())
                .map(Context::getSystem)
                .map(SystemState::getApplication)
                .map(Application::getApplicationId)
                .orElse(null);
        if (actual == null || !expected.equals(actual)) {
            log.warn("Alexa application id mismatch: expected prefix={}", abbreviate(expected, 24));
            throw new SecurityException("Request application id does not match configured skill");
        }
    }

    /** Resolve Alexa signature header (HTTP header names are case-insensitive). */
    public static String signatureFromHeaders(java.util.function.Function<String, String> getHeader) {
        String s = getHeader.apply(HDR_SIGNATURE);
        if (s != null && !s.isBlank()) {
            return s;
        }
        return getHeader.apply(HDR_SIGNATURE.toLowerCase());
    }

    public static String certUrlFromHeaders(java.util.function.Function<String, String> getHeader) {
        String s = getHeader.apply(HDR_CERT_CHAIN);
        if (s != null && !s.isBlank()) {
            return s;
        }
        return getHeader.apply("signaturecertchainurl");
    }

    public static String abbreviate(String value, int maxLen) {
        if (value == null) {
            return "null";
        }
        return value.length() <= maxLen ? value : value.substring(0, maxLen) + "...";
    }
}
