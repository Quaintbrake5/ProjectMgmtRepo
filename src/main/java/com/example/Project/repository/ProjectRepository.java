package com.example.Project.repository;

import com.example.Project.models.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsById(@NotNull Long projectId);

    @NotNull
    Optional<Project> findById(@NotNull Long projectId);

}
