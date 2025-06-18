package com.example.Project.repository;

import com.example.Project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find a user by email

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}