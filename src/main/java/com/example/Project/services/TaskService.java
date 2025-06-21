package com.example.Project.services;

import com.example.Project.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(String taskName, String taskDescription, Long projectId);

    Task updateTask(Long taskId, String taskName, String taskDescription);

    void deleteTask(Long taskId);

    void assignUserToTask(Long taskId, Long userId);

    void removeUserFromTask(Long taskId, Long userId);

    void assignTaskToProject(Long taskId, Long projectId);

    void removeTaskFromProject(Long taskId, Long projectId);

    // Get user tasks
    List<Task> getUserTasks(Long userId);

    List<Task> listAllTasks();

    Optional<Task> getTaskDetails(Long taskId);
}
