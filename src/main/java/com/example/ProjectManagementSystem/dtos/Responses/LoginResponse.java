package com.example.ProjectManagementSystem.dtos.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record LoginResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        String expiresIn,

        @JsonProperty("expires_at")
        Instant expiresAt

) {

}