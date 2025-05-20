package com.example.ProjectManagementSystem.dtos.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.time.Instant;

@Builder
public record LoginResponse(

        @JsonProperty("user_id")
        Long userId,

        @JsonProperty("email")
        String email,

        @JsonProperty("name")
        String name,

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Long expiresIn,

        @JsonProperty("expires_at")
        Instant expiresAt

) {
    public LoginResponse {
        if (email == null || name == null) {
            throw new IllegalArgumentException("All fields must be provided");
        }
    }
}