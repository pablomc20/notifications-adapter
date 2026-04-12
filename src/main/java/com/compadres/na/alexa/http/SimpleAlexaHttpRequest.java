package com.compadres.na.alexa.http;

import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.servlet.verifiers.AlexaHttpRequest;

/**
 * Adapts Spring MVC raw body + Alexa headers to {@link AlexaHttpRequest} without using the SDK's
 * {@code javax.servlet}-based {@code ServletRequest} (Spring 4 uses Jakarta).
 */
public final class SimpleAlexaHttpRequest implements AlexaHttpRequest {

    private final String baseEncoded64Signature;
    private final String signingCertificateChainUrl;
    private final byte[] serializedRequestEnvelope;
    private final RequestEnvelope deserializedRequestEnvelope;

    public SimpleAlexaHttpRequest(
            String baseEncoded64Signature,
            String signingCertificateChainUrl,
            byte[] serializedRequestEnvelope,
            RequestEnvelope deserializedRequestEnvelope) {
        this.baseEncoded64Signature = baseEncoded64Signature;
        this.signingCertificateChainUrl = signingCertificateChainUrl;
        this.serializedRequestEnvelope = serializedRequestEnvelope;
        this.deserializedRequestEnvelope = deserializedRequestEnvelope;
    }

    @Override
    public String getBaseEncoded64Signature() {
        return baseEncoded64Signature;
    }

    @Override
    public String getSigningCertificateChainUrl() {
        return signingCertificateChainUrl;
    }

    @Override
    public byte[] getSerializedRequestEnvelope() {
        return serializedRequestEnvelope;
    }

    @Override
    public RequestEnvelope getDeserializedRequestEnvelope() {
        return deserializedRequestEnvelope;
    }
}
