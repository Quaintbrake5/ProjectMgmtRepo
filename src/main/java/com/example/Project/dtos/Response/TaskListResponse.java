package com.example.Project.dtos.Response;

import com.example.Project.enums.TaskPriority;
import com.example.Project.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskListResponse(
        Long id,
        String title,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        String assigneeName
) {}

