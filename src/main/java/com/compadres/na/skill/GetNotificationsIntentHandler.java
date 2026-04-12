package com.compadres.na.skill;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.User;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.request.Predicates;
import com.compadres.na.service.alexa.NotificationService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class GetNotificationsIntentHandler implements RequestHandler {

    private final NotificationService notificationService;

    public GetNotificationsIntentHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("GetNotificationsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=GetNotificationsIntent");
        String userId = Optional.ofNullable(input.getRequestEnvelope().getContext())
                .map(Context::getSystem)
                .map(SystemState::getUser)
                .map(User::getUserId)
                .filter(id -> !id.isBlank())
                .orElse(null);

        if (userId == null) {
            return Optional.of(Response.builder()
                    .withOutputSpeech(SsmlOutputSpeech.builder()
                            .withSsml("<speak>No pude identificar tu cuenta de Alexa. Abre la skill de nuevo e inténtalo otra vez.</speak>")
                            .build())
                    .withShouldEndSession(true)
                    .build());
        }

        log.info("Alexa notifications lookup for userIdPrefix={}", userIdPrefix(userId));

        List<String> notificaciones = notificationService.getUnreadNotifications(userId);

        StringBuilder ssml = new StringBuilder("<speak>");
        if (notificaciones.isEmpty()) {
            ssml.append("No tienes notificaciones pendientes.");
        } else {
            ssml.append("Tienes ").append(notificaciones.size()).append(" notificaciones. ");
            for (String notificacion : notificaciones) {
                ssml.append(notificacion).append("<break time='500ms'/>");
            }
        }
        ssml.append("</speak>");

        return Optional.of(Response.builder()
                .withOutputSpeech(SsmlOutputSpeech.builder().withSsml(ssml.toString()).build())
                .withShouldEndSession(true)
                .build());
    }

    private static String userIdPrefix(String userId) {
        int n = Math.min(12, userId.length());
        return n <= 0 ? "?" : userId.substring(0, n) + "...";
    }
}
