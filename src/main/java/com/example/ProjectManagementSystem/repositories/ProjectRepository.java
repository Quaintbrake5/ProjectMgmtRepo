package com.example.ProjectManagementSystem.repositories;

import com.example.ProjectManagementSystem.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
