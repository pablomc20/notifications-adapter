package com.compadres.na.components.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonFallbackIntentHandler implements RequestHandler {

    private final AlexaResponseFactory responseFactory;

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=AMAZON.FallbackIntent");

        String message = "No te he entendido. Di: qué notificaciones tengo.";
        return Optional.of(responseFactory.ok(message));
    }
}