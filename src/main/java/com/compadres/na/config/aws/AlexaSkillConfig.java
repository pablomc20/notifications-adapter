package com.compadres.na.config.aws;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.services.Serializer;
import com.amazon.ask.model.services.util.JacksonSerializer;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlexaSkillConfig {

    @Bean
    public Skill alexaSkill(
            AlexaProperties alexaProperties,
            List<RequestHandler> handlers) {
        return Skills.standard()
        .addRequestHandlers(handlers.toArray(new RequestHandler[0]))
                .withSkillId(alexaProperties.getSkillId())
                .build();
    }

    @Bean
    public Serializer serializer() {
        return new JacksonSerializer();
    }
}