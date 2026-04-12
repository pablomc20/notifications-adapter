package com.compadres.na.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * With default {@code application.properties}, Alexa verification is enabled: unsigned requests must be rejected.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AlexaWebhookVerificationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void missingAlexaSignatureHeadersReturnsForbidden() throws Exception {
        byte[] body = new ClassPathResource("alexa/min-launch-request.json").getContentAsByteArray();
        mockMvc.perform(post("/alexa/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }
}
