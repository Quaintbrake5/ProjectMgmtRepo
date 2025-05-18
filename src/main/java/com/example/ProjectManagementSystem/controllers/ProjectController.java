package com.example.ProjectManagementSystem.controllers;

import com.example.ProjectManagementSystem.models.Project;
import com.example.ProjectManagementSystem.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    // add logic to create a project
    @PostMapping("/register")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project.getName(), project.getDescription()));
    }

    //add logic to update a project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectService.updateProject(id, project.getName(), project.getDescription()));
    }

    // add logic to delete a project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // add logic to assign a task to a project
    @PostMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<Void> assignTaskToProject(@PathVariable Long projectId, @PathVariable Long taskId) {
        projectService.assignTaskToProject(projectId, taskId);
        return ResponseEntity.ok().build();
    }

    //add  logic to remove a task from a project
    @DeleteMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromProject(@PathVariable Long projectId, @PathVariable Long taskId) {
        projectService.removeTaskFromProject(projectId, taskId);
        return ResponseEntity.noContent().build();
    }

    // add logic to list all projects
    @GetMapping
    public ResponseEntity<List<Project>> listAllProjects() {
        return ResponseEntity.ok(projectService.listAllProjects());
    }

    //add logic to get project details
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

}
