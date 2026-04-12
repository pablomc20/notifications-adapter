package com.compadres.na.skill;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;
import com.amazon.ask.request.Predicates;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AmazonFallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=AMAZON.FallbackIntent");
        return Optional.of(Response.builder()
                .withOutputSpeech(PlainTextOutputSpeech.builder()
                        .withText("No te he entendido. Di: qué notificaciones tengo.")
                        .build())
                .withShouldEndSession(false)
                .build());
    }
}
