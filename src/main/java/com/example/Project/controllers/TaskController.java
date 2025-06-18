package com.example.Project.controllers;

import com.example.Project.models.Task;
import com.example.Project.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")

    public ResponseEntity<Map<String, Long>> createTask(@RequestParam Long projectId, @RequestBody Task task) {
        Task createdTask = taskService.createTask(task.getTaskName(), task.getTaskDescription(), projectId);
        return ResponseEntity.ok(Map.of("taskId", createdTask.getTaskId()));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Optional<Task>> getTaskDetails(@PathVariable("taskId") Long taskId) {
        Optional<Task> task = taskService.getTaskDetails(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Task>> listAllTasks(){
        return ResponseEntity.ok(taskService.listAllTasks());
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task.getTaskName(), task.getTaskDescription());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    // add logic to assign user to task
    @PostMapping("/{taskId}/assign-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignUserToTask(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

    // add logic to unassign user from task
    @DeleteMapping("/{taskId}/remove-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUserFromTask(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.removeUserFromTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{taskId}/assign-task-to-project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignTaskToProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        taskService.assignTaskToProject(taskId, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}/remove-task-from-project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeTaskFromProject(@PathVariable Long taskId, @PathVariable Long projectId) {
        taskService.removeTaskFromProject(taskId, projectId);
        return ResponseEntity.ok().build();
    }

}
