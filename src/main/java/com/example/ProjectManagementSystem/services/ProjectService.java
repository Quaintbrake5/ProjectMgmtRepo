package com.example.ProjectManagementSystem.services;

public interface ProjectService {
    void createProject(String projectName, String projectDescription, String startDate, String endDate);

    void updateProject(Long projectId, String projectName, String projectDescription, String startDate, String endDate);

    void deleteProject(Long projectId);

    void assignTaskToProject(Long projectId, Long taskId);

    void removeTaskFromProject(Long projectId, Long taskId);

    void listAllProjects();

    void getProjectDetails(Long projectId);
}
