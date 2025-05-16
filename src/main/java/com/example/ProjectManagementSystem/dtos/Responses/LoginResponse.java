package com.example.ProjectManagementSystem.dtos.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.time.Instant;

@Builder
public record LoginResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        Long expiresIn,

        @JsonProperty("expires_at")
        Instant expiresAt

) {

}