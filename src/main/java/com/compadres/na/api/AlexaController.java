package com.compadres.na.api;

import com.amazon.ask.Skill;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.services.Serializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/alexa")
@RequiredArgsConstructor
public class AlexaController {

    private final Serializer serializer;
    private final Skill alexaSkill;

    @PostMapping(value = "/webhook", produces = "application/json")
    public ResponseEntity<byte[]> handleAlexaRequest(@RequestBody byte[] body) {
        try {
            // 1. Transformar el JSON crudo en el objeto RequestEnvelope de Amazon
            String jsonRequest = new String(body);
            RequestEnvelope requestEnvelope = serializer.deserialize(jsonRequest, RequestEnvelope.class);

            // 2. Delegar la ejecución a la Skill (Esto invoca mágicamente tu LaunchRequestHandler o IntentHandler)
            ResponseEnvelope responseEnvelope = alexaSkill.invoke(requestEnvelope);

            // 3. Serializar la respuesta de vuelta a bytes JSON
            byte[] responseBytes = serializer.serialize(responseEnvelope).getBytes();

            // 4. Retornar HTTP 200 OK a los servidores de Amazon
            return ResponseEntity.ok(responseBytes);

        } catch (Exception e) {
            log.error("Error procesando la petición de Alexa", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
