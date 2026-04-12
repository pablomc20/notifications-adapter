package com.compadres.na.models.alexa;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseBody {
    private OutputSpeech outputSpeech;
    private boolean shouldEndSession;
}
