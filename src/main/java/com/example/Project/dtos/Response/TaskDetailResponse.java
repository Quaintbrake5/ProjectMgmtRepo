package com.example.Project.dtos.Response;


import com.example.Project.enums.TaskStatus;
import com.example.Project.enums.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;


public record TaskDetailResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CreatorInfo creator,
        AssigneeInfo assignee,
        List<CommentInfo> comments

) {
    public record CreatorInfo(Long id, String name, String email) {}
    public record AssigneeInfo(Long id, String name, String email) {}
    public record CommentInfo(Long id, String authorName, String content, LocalDateTime createdAt) {}
}
