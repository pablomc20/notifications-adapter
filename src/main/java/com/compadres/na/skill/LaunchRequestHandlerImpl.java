package com.compadres.na.skill;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;
import com.amazon.ask.model.ui.Reprompt;
import com.amazon.ask.request.Predicates;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class LaunchRequestHandlerImpl implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=LaunchRequest");
        String speechText = "Bienvenido a tu sistema. Puedes preguntarme: ¿qué notificaciones tengo?";
        String reprompt = "¿Deseas escuchar tus notificaciones?";
        return Optional.of(Response.builder()
                .withOutputSpeech(PlainTextOutputSpeech.builder().withText(speechText).build())
                .withReprompt(Reprompt.builder()
                        .withOutputSpeech(PlainTextOutputSpeech.builder().withText(reprompt).build())
                        .build())
                .withShouldEndSession(false)
                .build());
    }
}
