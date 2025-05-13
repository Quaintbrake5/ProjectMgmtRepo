package com.example.ProjectManagementSystem.dtos.Requests;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @Email(message = "Must be a valid address!")
    @NotBlank(message = "Email mustn't be blank!")
    private String email;

    @NotBlank(message = "Password must not be blank!")
    private String password;

}