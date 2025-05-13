package com.example.ProjectManagementSystem.dtos.Responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private Long userId;
    private String message;
}
