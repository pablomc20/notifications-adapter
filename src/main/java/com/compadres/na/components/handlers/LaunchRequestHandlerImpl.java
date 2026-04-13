package com.compadres.na.components.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LaunchRequestHandlerImpl implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Bienvenido a tu sistema. Puedes preguntarme: ¿qué notificaciones tengo?";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withReprompt("¿Deseas escuchar tus notificaciones?")
                .build();
    }
}

