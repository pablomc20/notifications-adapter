package com.compadres.na;

import com.compadres.na.config.AlexaProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AlexaProperties.class)
public class NotificationsAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationsAdapterApplication.class, args);
	}

}
