package com.example.ProjectManagementSystem.services.impl;

import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.enums.UserStatus;
import com.example.ProjectManagementSystem.exceptions.UserAlreadyExistsException;
import com.example.ProjectManagementSystem.models.Role;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.RoleRepository;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.AuthService;
import com.example.ProjectManagementSystem.utils.PasswordUtil;
import lombok.*;

import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Responses.LoginResponse;
import com.example.ProjectManagementSystem.dtos.Responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 1. Check if user already exists
        if (userRepository.existByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "A user with email '" + request.getEmail() + "' already exists."
            );
        }  else {
            // 1.1 Check if email is valid
            if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            // 1.2 Check if password is strong
            if (request.getPassword().length() < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters long");
            }

            // 1.3 Check if first name and last name are not empty
            if (request.getFirstName().isEmpty() || request.getLastName().isEmpty()) {
                throw new IllegalArgumentException("First name and last name cannot be empty");
            }

            // 1.4 Check if email is not empty
            if (request.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
        }

        // 2. Fetch default role (ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found"));

        // 3. Build User entity
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                // encode password via util
                .passwordHash(PasswordUtil.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .roles(Collections.singleton(defaultRole))
                .build();

        // 4. Persist
        userRepository.save(user);

        return RegisterResponse.builder()
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Check if user exists
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. Check password
        if (!PasswordUtil.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        // 3. Build response
        return LoginResponse.builder()
                .message("Login successful")
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}

