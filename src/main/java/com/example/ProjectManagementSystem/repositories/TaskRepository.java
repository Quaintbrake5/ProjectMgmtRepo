package com.example.ProjectManagementSystem.repositories;

import com.example.ProjectManagementSystem.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.mapping.Table;


public interface TaskRepository extends JpaRepository<Task, Long> {

}
