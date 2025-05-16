package com.example.ProjectManagementSystem.services;

public interface TaskService {
    void createTask(Long taskId,String taskName, String taskDescription, String dueDate, Long projectId);

    void updateTask(Long taskId, String taskName, String taskDescription, String dueDate);

    void deleteTask(Long taskId);

    void assignUserToTask(Long taskId, Long userId);

    void removeUserFromTask(Long taskId, Long userId);

    void listAllTasks();

    void getTaskDetails(Long taskId);
}
