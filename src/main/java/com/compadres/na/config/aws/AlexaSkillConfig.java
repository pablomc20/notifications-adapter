package com.compadres.na.config.aws;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.compadres.na.client.GetNotificationsIntentHandler;
import com.compadres.na.client.LaunchRequestHandlerImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlexaSkillConfig {

    @Bean
    public Skill alexaSkill(LaunchRequestHandlerImpl launchRequestHandler,
                            GetNotificationsIntentHandler getNotificationsIntentHandler) {
        return Skills.standard()
                .addRequestHandlers(
                        launchRequestHandler,
                        getNotificationsIntentHandler
                )
                // Evita procesar peticiones que no vengan de tu Skill real
                // .withSkillId("amzn1.ask.skill.XXXXX-XXXXX") 
                .build();
    }

}