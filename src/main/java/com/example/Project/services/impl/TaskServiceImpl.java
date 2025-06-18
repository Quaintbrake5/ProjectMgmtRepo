package com.example.Project.services.impl;

import com.example.Project.exceptions.UserNotFoundException;
import com.example.Project.models.Project;
import com.example.Project.models.Task;
import com.example.Project.repository.ProjectRepository;
import com.example.Project.repository.TaskRepository;
import com.example.Project.repository.UserRepository;
import com.example.Project.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl  implements TaskService {
    @Autowired private TaskRepository taskRepo;
    @Autowired private  UserRepository repo;
    @Autowired private ProjectRepository projectRepo;

    @Override
    public Task createTask(String taskName, String taskDescription, Long projectId) {
         Task task = new Task();
         task.setTaskName(taskName);
         task.setTaskDescription(taskDescription);
         taskRepo.save(task);
         return task;
    }

    @Override
    @Transactional
    public Task updateTask(Long taskId, String taskName, String taskDescription) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        taskRepo.save(task);
        return task;
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepo.existsById(taskId)) {
            throw new NoSuchElementException("Task not found with id: " + taskId);
        }
        taskRepo.deleteById(taskId);
    }

    @Override
    @Transactional
    public void assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        var user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (task.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is already assigned to this task");
        }

        task.getUsers().add(user);
        taskRepo.save(task);
    }

    @Override
    @Transactional
    public void removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        var user = repo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!task.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is not assigned to this task");
        }

        task.getUsers().remove(user);
        taskRepo.save(task);
    }

    @Override
    @Transactional
    public void assignTaskToProject(Long taskId, Long projectId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));

        if (task.getProjects().contains(project)) {
            throw new IllegalArgumentException("Project is already assigned to this task");
        }

        task.getProjects().add(project);
        taskRepo.save(task);
    }

@Override
@Transactional
public void removeTaskFromProject(Long taskId, Long projectId) {
    Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
    Project project = projectRepo.findById(projectId)
            .orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));

    if (!task.getProjects().contains(project)) {
        throw new IllegalArgumentException("Project is not assigned to this task");
    }

    task.getProjects().remove(project);
    taskRepo.save(task);
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
        System.out.println("Description: " + task.getTaskDescription());
        return Optional.of(task);
    }
}
