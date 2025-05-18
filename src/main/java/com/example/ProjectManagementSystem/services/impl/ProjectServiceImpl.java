package com.example.ProjectManagementSystem.services.impl;

import java.lang.*;

import com.example.ProjectManagementSystem.models.Project;
import com.example.ProjectManagementSystem.models.Task;
import com.example.ProjectManagementSystem.repositories.ProjectRepository;
import com.example.ProjectManagementSystem.repositories.TaskRepository;
import com.example.ProjectManagementSystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired private ProjectRepository projectRepo;
    @Autowired private TaskRepository taskRepo;

    @Override
    public Project createProject(String projectName, String projectDescription) {
        // Validate input parameters
        if (projectName == null || projectName.isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (projectDescription == null || projectDescription.isEmpty()) {
            throw new IllegalArgumentException("Project description cannot be null or empty");
        }

        // Create and save the project
        Project project = new Project();
        project.setName(projectName);
        project.setDescription(projectDescription);
        projectRepo.save(project);
        return project;
    }

    @Override
    public Project updateProject(Long projectId, String projectName, String projectDescription) {
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.setName(projectName);
            project.setDescription(projectDescription);
            projectRepo.save(project);
        } else {
            throw new NoSuchElementException("Project not found with id: " + projectId);
        }
        return projectOpt.get();
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepo.deleteById(projectId);
    }

    @Override
    public void assignTaskToProject(Long projectId, Long taskId) {
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            // Assume getTasks() returns a List<Task> and TaskRepository is available
            Task task = taskRepo.findById(taskId)
                    .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
            project.getTasks().add(task);
            projectRepo.save(project);
        } else {
            throw new NoSuchElementException("Project not found with id: " + projectId);
        }
    }

    @Override
    public void removeTaskFromProject(Long projectId, Long taskId) {
        Optional<Project> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.getTasks().removeIf(task -> task.getTaskId().equals(taskId));
            projectRepo.save(project);
        } else {
            throw new NoSuchElementException("Project not found with id: " + projectId);
        }
    }

    @Override
    public List<Project> listAllProjects() {
        List<Project> projects = projectRepo.findAll();
        projects.forEach(System.out::println);
        return projects;
    }

    @Override
    public Optional<Project> getProjectDetails(Long projectId) {
        return projectRepo.findById(projectId);
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepo.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
    }
}
