package com.example.Project.dtos.Response;

import com.example.Project.enums.ProjectPriority;
import com.example.Project.enums.ProjectStatus;
import com.example.Project.enums.TaskPriority;
import com.example.Project.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDetailResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ProjectDetailResponse.CreatorInfo creator,
        ProjectDetailResponse.AssigneeInfo assignee,
        List<ProjectDetailResponse.CommentInfo> comments

) {
    public record CreatorInfo(Long id, String name, String email) {}
    public record AssigneeInfo(Long id, String name, String email) {}
    public record CommentInfo(Long id, String authorName, String content, LocalDateTime createdAt) {}
}