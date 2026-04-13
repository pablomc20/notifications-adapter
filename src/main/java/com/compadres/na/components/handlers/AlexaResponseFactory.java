package com.compadres.na.components.handlers;

import org.springframework.stereotype.Component;

import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;
import com.amazon.ask.model.ui.SimpleCard;

@Component
public class AlexaResponseFactory {

    public Response ok(String speechText) {
        return Response.builder()
                .withOutputSpeech(plainText(speechText))
                .withShouldEndSession(false)
                .build();
    }

    public Response end(String speechText) {
        return Response.builder()
                .withOutputSpeech(plainText(speechText))
                .withShouldEndSession(true)
                .build();
    }

    public Response withCard(String speechText, String title) {
        return Response.builder()
                .withOutputSpeech(plainText(speechText))
                .withCard(SimpleCard.builder()
                        .withTitle(title)
                        .withContent(speechText)
                        .build())
                .withShouldEndSession(false)
                .build();
    }

    private PlainTextOutputSpeech plainText(String text) {
        return PlainTextOutputSpeech.builder()
                .withText(text)
                .build();
    }
}