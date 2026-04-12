package com.compadres.na.service.alexa;

import org.springframework.stereotype.Service;

import com.compadres.na.models.alexa.AlexaRequest;
import com.compadres.na.models.alexa.AlexaResponse;
import com.compadres.na.models.alexa.OutputSpeech;
import com.compadres.na.models.alexa.ResponseBody;

@Service
public class AlexaService {

    public AlexaResponse processRequest(AlexaRequest request) {

        String intentName = request.getRequest().getIntent().getName();

        if ("LaunchRequest".equals(request.getRequest().getType())) {
            return buildResponse("Bienvenido a Carpintería Marqz");
        }

        String speechText;

        switch (intentName) {
            case "GetNotificationsIntent":
                speechText = "Tu mueble Silla hermética está en proceso de diseño";
                break;

            default:
                speechText = "No entendí tu solicitud";
        }

        return buildResponse(speechText);
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