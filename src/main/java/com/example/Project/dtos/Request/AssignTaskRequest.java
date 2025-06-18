package com.example.Project.dtos.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AssignTaskRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Assignee email is required")
        String assigneeEmail
) {}
