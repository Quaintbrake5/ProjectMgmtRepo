package com.example.Project.services;

import com.example.Project.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project createProject(String projectName, String projectDescription);

    Project updateProject(Long projectId, String projectName, String projectDescription);

    void deleteProject(Long projectId);

    void assignTaskToProject(Long projectId, Long taskId);

    void removeTaskFromProject(Long projectId, Long taskId);

    void assignUserToProject(Long projectId, Long userId);

    void removeUserFromProject(Long projectId, Long userId);

    // Fetch user's projects
    List<Project> getUserProjects(Long userId);

    List<Project> listAllProjects();

    Optional<Project> getProjectById(Long projectId);
}