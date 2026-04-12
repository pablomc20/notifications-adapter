package com.compadres.na.skill;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import com.amazon.ask.request.Predicates;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class SessionEndedRequestHandlerImpl implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        SessionEndedRequest req = (SessionEndedRequest) input.getRequestEnvelope().getRequest();
        log.info("Alexa handler=SessionEnded reason={}", req.getReason());
        return Optional.empty();
    }
}
