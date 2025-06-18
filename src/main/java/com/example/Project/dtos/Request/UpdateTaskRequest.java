package com.example.Project.dtos.Request;

import com.example.Project.enums.TaskPriority;
import com.example.Project.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;

public record UpdateTaskRequest(
        @Size(min = 1, message = "Title cannot be empty if provided")
        String title,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description,

        @FutureOrPresent(message = "Due date cannot be in the past")
        LocalDateTime dueDate,

        TaskPriority priority,
        TaskStatus status
) {
}
