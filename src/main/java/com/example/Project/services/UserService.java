package com.example.Project.services;

import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.UpdateProfileRequest;
import com.example.Project.models.User;
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

    void assignProjectToUser(Long userId, Long projectId);
    void removeProjectFromUser(Long userId, Long projectId);

    User updateProfile (String email, UpdateProfileRequest updatedInfo);

    void assignTaskToUser(Long userId, Long TaskId);
    void removeTaskFromUser(Long userId, Long TaskId);

}