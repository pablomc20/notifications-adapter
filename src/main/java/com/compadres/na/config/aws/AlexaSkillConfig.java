package com.compadres.na.config.aws;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.model.services.Serializer;
import com.amazon.ask.util.JacksonSerializer;
import com.compadres.na.config.AlexaProperties;
import com.compadres.na.skill.AmazonFallbackIntentHandler;
import com.compadres.na.skill.AmazonHelpIntentHandler;
import com.compadres.na.skill.AmazonStopCancelIntentHandler;
import com.compadres.na.skill.GetNotificationsIntentHandler;
import com.compadres.na.skill.LaunchRequestHandlerImpl;
import com.compadres.na.skill.SessionEndedRequestHandlerImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlexaSkillConfig {

    @Bean
    public Skill alexaSkill(
            AlexaProperties alexaProperties,
            SessionEndedRequestHandlerImpl sessionEndedRequestHandler,
            AmazonHelpIntentHandler amazonHelpIntentHandler,
            AmazonStopCancelIntentHandler amazonStopCancelIntentHandler,
            LaunchRequestHandlerImpl launchRequestHandler,
            GetNotificationsIntentHandler getNotificationsIntentHandler,
            AmazonFallbackIntentHandler amazonFallbackIntentHandler) {
        return Skills.standard()
                .addRequestHandlers(
                        sessionEndedRequestHandler,
                        amazonHelpIntentHandler,
                        amazonStopCancelIntentHandler,
                        launchRequestHandler,
                        getNotificationsIntentHandler,
                        amazonFallbackIntentHandler)
                .withSkillId(alexaProperties.getSkillId())
                .build();
    }

    @Bean
    public Serializer alexaSerializer() {
        return new JacksonSerializer();
    }
}
