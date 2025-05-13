package com.example.ProjectManagementSystem.repositories;

import com.example.ProjectManagementSystem.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
