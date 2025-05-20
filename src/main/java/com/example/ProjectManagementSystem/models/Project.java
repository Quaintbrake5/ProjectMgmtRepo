package com.example.ProjectManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
//import com.example.ProjectManagementSystem.models.Task;
@Getter
@Entity
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Project")
@Builder
@AllArgsConstructor


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
}
