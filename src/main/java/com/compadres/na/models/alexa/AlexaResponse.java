package com.compadres.na.models.alexa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlexaResponse {
    private String version;
    private ResponseBody response;
}
