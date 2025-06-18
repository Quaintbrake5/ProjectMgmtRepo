package com.example.Project.repository;

import com.example.Project.models.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsById(@NotNull Long taskId);
    // Additional query methods can be defined here if needed

    @NotNull
    Optional<Task> findById(@NotNull Long taskId);

}
