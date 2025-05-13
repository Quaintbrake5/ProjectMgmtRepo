package com.example.ProjectManagementSystem.repositories;

import com.example.ProjectManagementSystem.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
