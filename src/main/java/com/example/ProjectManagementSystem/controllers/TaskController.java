package com.example.ProjectManagementSystem.controllers;

import com.example.ProjectManagementSystem.models.Task;
import com.example.ProjectManagementSystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/register")
    public ResponseEntity<Void> createTask(@RequestParam Long projectId, @RequestBody Task task) {
        taskService.createTask(task.getTaskName(), task.getDescription(), projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Optional<Task>> getTaskDetails(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskDetails(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> listAllTasks(){
        return ResponseEntity.ok(taskService.listAllTasks());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task.getTaskName(), task.getDescription());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    // add logic to assign user to task
    @PostMapping("/{taskId}/assign-user/{userId}")
    public ResponseEntity<Void> assignUserToTask(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

    // add logic to unassign user from task
    @DeleteMapping("/{taskId}/remove-user/{userId}")
    public ResponseEntity<Void> removeUserFromTask(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.removeUserFromTask(taskId, userId);
        return ResponseEntity.ok().build();
    }

}
