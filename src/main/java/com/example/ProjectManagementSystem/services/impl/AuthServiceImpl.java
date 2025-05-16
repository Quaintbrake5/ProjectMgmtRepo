package com.example.ProjectManagementSystem.services.impl;

import com.example.ProjectManagementSystem.models.Role;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.RoleRepository;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.AuthService;
import lombok.*;

import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.dtos.Responses.LoginResponse;
import com.example.ProjectManagementSystem.dtos.Responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class AuthServiceImpl implements AuthService {
    @Override
    public RegisterResponse register(RegisterRequest request) {
        @Autowired private UserRepository userRepository;
        @Autowired private RoleRepository roleRepository;


        // Implement registration logic here
        // 1. Check if user already exists
        // 2. Fetch default role (ROLE_USER)
        // 3. Build User entity
        // 4. Persist user to the database
        // 5. Return response

        // 1. Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists with email: " + request.getEmail());
        }

        // 2. Fetch default role (ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        // 3. Build User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        // 4. Persist user to the database
        userRepository.save(user);

        // 5. Return response
        return new RegisterResponse(user.getUserId(), user.getEmail(), "User registered successfully");

        return new RegisterResponse();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Implement login logic here
        return new LoginResponse();
    }


}
