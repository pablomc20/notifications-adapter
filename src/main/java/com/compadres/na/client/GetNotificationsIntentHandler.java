package com.compadres.na.client;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.compadres.na.service.alexa.NotificationService;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetNotificationsIntentHandler implements RequestHandler {

    private final NotificationService notificationService;

    // Inyectamos el servicio simulado
    public GetNotificationsIntentHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        // El nombre debe coincidir EXACTAMENTE con el intent creado en la Consola de Alexa
        return input.matches(Predicates.intentName("GetNotificationsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        // En una app real, sacarías el ID así: input.getRequestEnvelope().getContext().getSystem().getUser().getId();
        String fakeUserId = "amzn1.123"; 
        
        List<String> notificaciones = notificationService.getUnreadNotifications(fakeUserId);

        StringBuilder respuestaBuilder = new StringBuilder("<speak>");
        if (notificaciones.isEmpty()) {
            respuestaBuilder.append("No tienes notificaciones pendientes.");
        } else {
            respuestaBuilder.append("Tienes ").append(notificaciones.size()).append(" notificaciones. ");
            for (String notificacion : notificaciones) {
                // El break hace una pausa de medio segundo para que suene natural
                respuestaBuilder.append(notificacion).append("<break time='500ms'/>");
            }
        }
        respuestaBuilder.append("</speak>");

        return input.getResponseBuilder()
                .withSpeech(respuestaBuilder.toString())
                .withShouldEndSession(true) // Termina la interacción después de leer
                .build();
    }
}