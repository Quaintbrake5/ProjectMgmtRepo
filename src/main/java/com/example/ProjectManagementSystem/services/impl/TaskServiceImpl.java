package com.example.ProjectManagementSystem.services.impl;

import com.example.ProjectManagementSystem.exceptions.UserNotFoundException;
import com.example.ProjectManagementSystem.models.Task;
import com.example.ProjectManagementSystem.repositories.TaskRepository;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskServiceImpl  implements TaskService {
    @Autowired private TaskRepository taskRepo;
    @Autowired private UserRepository repo;

    @Override
    public Task createTask(String taskName, String taskDescription, Long projectId) {
         Task task = new Task();
         task.setTaskName(taskName);
         task.setDescription(taskDescription);
         taskRepo.save(task);
         return task;
    }

    @Override
    @Transactional
    public Task updateTask(Long taskId, String taskName, String taskDescription) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        task.setTaskName(taskName);
        task.setDescription(taskDescription);
        taskRepo.save(task);
        return task;
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);

        if (!taskRepo.existsById(taskId)) {
            throw new NoSuchElementException("Task not found with id: " + taskId);
        }
    }

    @Override
    public void assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        // Assuming Task has a method to add a user
        task.addUser(userId);
        taskRepo.save(task);

        // Assuming User has a method to add a task
        if (task.getAssignedTo() == null) {
            task.setAssignedTo(repo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)));
        } else {
            throw new UserNotFoundException("User already assigned to task: " + taskId);
        }
    }

    @Override
    @Transactional
    public void removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        // For single user assignment, just set assignedTo to null if the user matches
        if (task.getAssignedTo() != null && task.getAssignedTo().getUserId().equals(userId)) {
            task.setAssignedTo(null);
            taskRepo.save(task);
        } else {
            throw new UserNotFoundException("User not found with id: " + userId + " in task: " + taskId);
        }

    }

    @Override
    public List<Task> listAllTasks() {
        List<Task> tasks = taskRepo.findAll();
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getTaskId() + ", Task Name: " + task.getTaskName());
        }
        return tasks;
    }

    @Override
    public Optional<Task> getTaskDetails(Long taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        System.out.println("Task ID: " + task.getTaskId());
        System.out.println("Task Name: " + task.getTaskName());
        System.out.println("Description: " + task.getDescription());
        return Optional.of(task);
    }
}
