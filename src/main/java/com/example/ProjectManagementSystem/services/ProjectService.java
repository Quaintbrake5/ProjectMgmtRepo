package com.example.ProjectManagementSystem.services;

import com.example.ProjectManagementSystem.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project createProject(String projectName, String projectDescription);

    Project updateProject(Long projectId, String projectName, String projectDescription);

    void deleteProject(Long projectId);

    void assignTaskToProject(Long projectId, Long taskId);

    void removeTaskFromProject(Long projectId, Long taskId);

    List<Project> listAllProjects();

    Optional<Project> getProjectDetails(Long projectId);

    Project getProjectById(Long projectId);
}
