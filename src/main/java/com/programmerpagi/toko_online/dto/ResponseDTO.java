package com.programmerpagi.toko_online.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {

    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Object data;

}
