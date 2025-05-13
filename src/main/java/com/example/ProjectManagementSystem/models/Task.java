package com.example.ProjectManagementSystem.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String status;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedTo;
}