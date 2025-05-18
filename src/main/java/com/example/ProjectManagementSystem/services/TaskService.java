package com.example.ProjectManagementSystem.services;

import com.example.ProjectManagementSystem.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(String taskName, String taskDescription, Long projectId);

    Task updateTask(Long taskId, String taskName, String taskDescription);

    void deleteTask(Long taskId);

    void assignUserToTask(Long taskId, Long userId);

    void removeUserFromTask(Long taskId, Long userId);

    List<Task> listAllTasks();

    Optional<Task> getTaskDetails(Long taskId);
}
