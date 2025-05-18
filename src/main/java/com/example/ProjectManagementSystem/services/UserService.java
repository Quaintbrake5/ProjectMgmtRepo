package com.example.ProjectManagementSystem.services;


import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.models.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User register(@Valid RegisterRequest user);
    User login(@Valid LoginRequest user);

    Optional<User> findByEmail(String email);

    List<User> getAll();
    Optional<User> getSingleUser(Long userId);
    void deleteUser(Long userId);
    User updateUser(User user, Long userId);
}
