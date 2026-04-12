package com.compadres.na.api;

import com.amazon.ask.Skill;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.services.Serializer;
import com.compadres.na.alexa.AlexaWebhookSecurityService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/alexa")
@RequiredArgsConstructor
public class AlexaController {

    private static final String CONTENT_JSON_UTF8 = "application/json;charset=UTF-8";

    private final Serializer serializer;
    private final Skill alexaSkill;
    private final AlexaWebhookSecurityService alexaWebhookSecurityService;

    @PostMapping(value = "/webhook", produces = "application/json;charset=UTF-8")
    public ResponseEntity<byte[]> handleAlexaRequest(HttpServletRequest httpRequest, @RequestBody byte[] body) {
        try {
            if (body == null || body.length == 0) {
                return ResponseEntity.badRequest().build();
            }

            String jsonRequest = new String(body, StandardCharsets.UTF_8);
            RequestEnvelope requestEnvelope = serializer.deserialize(jsonRequest, RequestEnvelope.class);

            String signature = AlexaWebhookSecurityService.signatureFromHeaders(httpRequest::getHeader);
            String certChainUrl = AlexaWebhookSecurityService.certUrlFromHeaders(httpRequest::getHeader);
            alexaWebhookSecurityService.verifyIfEnabled(requestEnvelope, body, signature, certChainUrl);

            log.info(
                    "Alexa webhook request type={}",
                    requestEnvelope.getRequest() != null ? requestEnvelope.getRequest().getType() : "null");

            ResponseEnvelope responseEnvelope = alexaSkill.invoke(requestEnvelope);
            byte[] responseBytes = serializer.serialize(responseEnvelope).getBytes(StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, CONTENT_JSON_UTF8)
                    .body(responseBytes);
        } catch (SecurityException e) {
            log.warn("Alexa webhook rejected: {}", e.getMessage());
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            log.error("Error procesando la petición de Alexa", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
