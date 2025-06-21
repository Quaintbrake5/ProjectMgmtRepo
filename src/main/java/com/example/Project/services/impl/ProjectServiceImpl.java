package com.example.Project.services.impl;

import com.example.Project.models.Project;
import com.example.Project.models.Task;
import com.example.Project.models.User;
import com.example.Project.repository.ProjectRepository;
import com.example.Project.repository.TaskRepository;
import com.example.Project.repository.UserRepository;
import com.example.Project.services.ProjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Autowired private ProjectRepository projectRepo;
    @Autowired private TaskRepository taskRepo;
    @Autowired private final UserRepository userRepo;

    @Override
    public Project createProject(String projectName, String projectDescription) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        projectRepo.save(project);
        return project;
    }

    @Override
    public Project updateProject(Long projectId, String projectName, String projectDescription) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        projectRepo.save(project);
        return project;
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepo.existsById(projectId)) {
            throw new NoSuchElementException("Project not found with id: " + projectId);
        }
        projectRepo.deleteById(projectId);
    }

    @Override
    public void assignTaskToProject(Long projectId, Long taskId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        if (project.getTasks() == null) {
            project.setTasks(new HashSet<>());
        }
        project.getTasks().add(task);
        task.setProject(project);
        projectRepo.save(project);
        taskRepo.save(task);
    }

    @Override
    public void removeTaskFromProject(Long projectId, Long taskId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));
        if (project.getTasks() != null && project.getTasks().remove(task)) {
            task.setProject(null); // Remove the association from the task
            taskRepo.save(task);
            projectRepo.save(project);
        } else {
            throw new NoSuchElementException("Task not found in project with id: " + projectId);
        }
    }

    @Override
    public void assignUserToProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
        assert userRepo != null;
        User users = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        if (project.getUsers() == null) {
            project.setUsers(new HashSet<>());
        }
        project.getUsers().add(users);
        users.setProject(project);
        projectRepo.save(project);
        userRepo.save(users);    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new NoSuchElementException("Project not found with id: " + projectId));
        assert userRepo != null;
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        if (project.getUsers() != null && project.getUsers().remove(user)) {
            user.setProject(null); // Remove the association from the user
            userRepo.save(user);
            projectRepo.save(project);
        } else {
            throw new NoSuchElementException("User not found in project with id: " + projectId);
        }
    }

    @Override
    public List<Project> getUserProjects(Long userId) {
        User user = null;
        if (userRepo != null) {
            user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        }
        List<Project> projects = null;
        if (user != null) {
            projects = new ArrayList<>(user.getProjects());
        }
        if (projects != null && projects.isEmpty()) {
            throw new NoSuchElementException("No projects found for user with id: " + userId);
        }
        return projects;
    }


    @Override
    public List<Project> listAllProjects() {
        List<Project> projects = projectRepo.findAll();
        if (projects.isEmpty()) {
            throw new NoSuchElementException("No projects found");
        }
        return projects;

}

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepo.findById(projectId);
    }
}