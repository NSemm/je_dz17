package com.k7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private String status;
    private String error;
    private String token;

    @Data
    public static class LoginRequest {
        private String login;
        private String password;
    }

}
