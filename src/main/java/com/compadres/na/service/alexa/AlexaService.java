package com.compadres.na.service.alexa;

import org.springframework.stereotype.Service;

import com.compadres.na.models.alexa.AlexaRequest;
import com.compadres.na.models.alexa.AlexaResponse;
import com.compadres.na.models.alexa.OutputSpeech;
import com.compadres.na.models.alexa.ResponseBody;

@Service
public class AlexaService {

    public AlexaResponse processRequest(AlexaRequest request) {

        String type = request.getRequest().getType();

        // 1. Manejamos el LaunchRequest primero (aquí NO hay intent)
        if ("LaunchRequest".equals(type)) {
            return buildResponse("Bienvenido a Carpintería Marqz");
        }

        // 2. Si es un IntentRequest, entonces SÍ buscamos el nombre
        if ("IntentRequest".equals(type) && request.getRequest().getIntent() != null) {
            String intentName = request.getRequest().getIntent().getName();

            switch (intentName) {
                case "GetNotificationsIntent":
                    return buildResponse("Tu mueble Silla hermética está en proceso de diseño");
                default:
                    return buildResponse("No entendí tu solicitud");
            }
        }

        return buildResponse("Lo siento, ocurrió un error inesperado.");
    }

    private AlexaResponse buildResponse(String text) {
        return AlexaResponse.builder()
                .version("1.0")
                .response(
                        ResponseBody.builder()
                                .outputSpeech(
                                        OutputSpeech.builder()
                                                .type("PlainText")
                                                .text(text)
                                                .build())
                                .shouldEndSession(true)
                                .build())
                .build();
    }
}