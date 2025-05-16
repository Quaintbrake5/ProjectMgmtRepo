package com.example.ProjectManagementSystem.dtos.Requests;

import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok. *;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "First name mustn't be empty!")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Email(message = "Must be a valid email address")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 8 characters")
    private String password;

//    @NotBlank(message = "Confirm password must not be empty")
//    @Size(min = 6, message = "Confirm password must be at least 8 characters")
//    private String confirmPassword;
}
