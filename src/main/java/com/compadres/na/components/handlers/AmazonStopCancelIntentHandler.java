package com.compadres.na.components.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonStopCancelIntentHandler implements RequestHandler {

    private final AlexaResponseFactory responseFactory;

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.CancelIntent")
                .or(intentName("AMAZON.StopIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=AMAZON.StopOrCancel");
        String message = "De acuerdo, hasta luego.";

        return Optional.of(responseFactory.end(message));
    }
}