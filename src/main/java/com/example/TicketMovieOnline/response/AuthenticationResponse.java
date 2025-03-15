package com.example.TicketMovieOnline.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("message")
    private String message;

    public AuthenticationResponse(String accessToken, String message, String refreshToken) {
        this.accessToken = accessToken;
        this.message = message;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}
