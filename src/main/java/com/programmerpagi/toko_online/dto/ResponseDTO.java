package com.programmerpagi.toko_online.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Object data;

    @Setter
    @Getter
    public static class LoginRequestDTO {

        private String username;
        private String password;
    }
}
