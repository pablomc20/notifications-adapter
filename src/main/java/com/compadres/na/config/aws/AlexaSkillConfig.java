package com.compadres.na.config.aws;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.compadres.na.components.handlers.AmazonFallbackIntentHandler;
import com.compadres.na.components.handlers.AmazonHelpIntentHandler;
import com.compadres.na.components.handlers.AmazonStopCancelIntentHandler;
import com.compadres.na.components.handlers.GetNotificationsIntentHandler;
import com.compadres.na.components.handlers.LaunchRequestHandlerImpl;
import com.compadres.na.components.handlers.SessionEndedRequestHandlerImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlexaSkillConfig {

    @Bean
    public Skill alexaSkill(
            AlexaProperties alexaProperties,
            LaunchRequestHandlerImpl launchRequestHandler,
            GetNotificationsIntentHandler getNotificationsIntentHandler,
            SessionEndedRequestHandlerImpl sessionEndedRequestHandlerImpl,
            AmazonFallbackIntentHandler amazonFallbackIntentHandler,
            AmazonStopCancelIntentHandler amazonStopCancelIntentHandler,
            AmazonHelpIntentHandler amazonHelpIntentHandler) {
        return Skills.standard()
                .addRequestHandlers(
                    launchRequestHandler,
                    getNotificationsIntentHandler,
                    sessionEndedRequestHandlerImpl,
                    amazonFallbackIntentHandler,
                    amazonHelpIntentHandler,
                    amazonStopCancelIntentHandler
                )
                .withSkillId(alexaProperties.getSkillId())
                .build();
    }

}