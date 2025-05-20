package com.example.ProjectManagementSystem.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
@AllArgsConstructor
@Builder


public class Task {
    /** Unique identifier for the task */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String taskName;
    private String status;
    private String description;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedTo;


    public Task(Long taskId, String taskName, String status, Project project, User assignedTo, String description) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.project = project;
        this.assignedTo = assignedTo;
        this.description = description;
//        this.dueDate = dueDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void addUser(Long userId) {
        // Logic to add a user to the task
        // This could involve updating the assignedTo field or any other relevant logic
        // For example:
        // this.assignedTo = userService.findById(userId);
        // Or any other logic to associate the user with the task
        // For example, if you have a UserService to fetch user details:
        // User user = userService.findById(userId);
        // this.assignedTo = user;
        // Or if you have a many-to-many relationship, you might want to add the user to a list of users
        // this.assignedUsers.add(user);
        // Ensure to save the task after modifying it
        // taskRepository.save(this);
        // Example:
        // this.assignedTo = userService.findById(userId);
        // taskRepository.save(this);
    }

//    public void removeUser(Long userId) {
//        users.removeIf(user -> user.getUserId().equals(userId));
//    }
}

