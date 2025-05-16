package com.example.ProjectManagementSystem.controllers;

import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.dtos.Responses.LoginResponse;
import com.example.ProjectManagementSystem.dtos.Responses.RegisterResponse;
import com.example.ProjectManagementSystem.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
