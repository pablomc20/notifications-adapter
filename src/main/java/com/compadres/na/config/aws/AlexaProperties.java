package com.compadres.na.config.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Externalized Alexa skill settings. Bind from {@code alexa.*} in application properties.
 */
@Component
@ConfigurationProperties(prefix = "alexa")
public class AlexaProperties {

    /**
     * Skill ID (amzn1.ask.skill....) — must match the skill in the Alexa developer console.
     */
    private String skillId = "";

    private final Verification verification = new Verification();

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public Verification getVerification() {
        return verification;
    }

    public static class Verification {

        /**
         * When false, signature and timestamp verification are skipped (e.g. {@code dev} / {@code test} profile).
         */
        private boolean enabled = true;

        private int timestampToleranceSeconds = 150;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getTimestampToleranceSeconds() {
            return timestampToleranceSeconds;
        }

        public void setTimestampToleranceSeconds(int timestampToleranceSeconds) {
            this.timestampToleranceSeconds = timestampToleranceSeconds;
        }
    }
}