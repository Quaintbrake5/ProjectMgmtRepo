package com.example.ProjectManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProjectManagementSystem.models.Role;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
