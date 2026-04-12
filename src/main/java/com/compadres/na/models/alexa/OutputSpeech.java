package com.compadres.na.models.alexa;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OutputSpeech {
    private String type;
    private String text;
}
