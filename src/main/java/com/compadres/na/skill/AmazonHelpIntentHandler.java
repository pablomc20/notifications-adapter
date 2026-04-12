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
public class AmazonHelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.info("Alexa handler=AMAZON.HelpIntent");
        String text = "Puedes decir: qué notificaciones tengo, para escuchar tus avisos pendientes.";
        return Optional.of(Response.builder()
                .withOutputSpeech(PlainTextOutputSpeech.builder().withText(text).build())
                .withShouldEndSession(false)
                .build());
    }
}
