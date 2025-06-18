package com.example.Project.dtos.Request;

import com.example.Project.enums.ProjectPriority;
import com.example.Project.enums.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateProjectRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        @FutureOrPresent(message = "Due date cannot be in the past")
        LocalDateTime dueDate,

        @NotNull(message = "Priority is required")
        ProjectPriority priority
) {
}
