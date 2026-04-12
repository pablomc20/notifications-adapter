package com.compadres.na.models.alexa;

import lombok.Data;

@Data
public class RequestBody {
    private String type;
    private Intent intent;
}
