package com.example.ProjectManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Project")


public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String name;
    private String description;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();

    public Project(Long projectId, String name, String description, User owner, List<Task> tasks) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.tasks = tasks;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
