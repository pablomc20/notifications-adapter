package com.compadres.na.skill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetNotificationsIntentHandlerEscapeTest {

    @Test
    void escapesAmpersandAngleBrackets() {
        assertEquals("a &amp; b &lt;c&gt;", GetNotificationsIntentHandler.escapeSsmlPlainText("a & b <c>"));
    }

    @Test
    void escapesAmpersandFirstSoEntitiesStayStable() {
        assertEquals("&amp;lt;not a tag&amp;gt;", GetNotificationsIntentHandler.escapeSsmlPlainText("&lt;not a tag&gt;"));
    }

    @Test
    void nullBecomesEmpty() {
        assertEquals("", GetNotificationsIntentHandler.escapeSsmlPlainText(null));
    }
}
