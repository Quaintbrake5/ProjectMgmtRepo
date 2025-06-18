package com.example.Project.controllers;

import com.example.Project.dtos.Request.ForgotPasswordRequest;
import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.ResetPasswordRequest;
import com.example.Project.dtos.Response.ForgotPasswordResponse;
import com.example.Project.dtos.Response.LoginResponse;
import com.example.Project.dtos.Response.RegisterResponse;
import com.example.Project.dtos.Response.ResetPasswordResponse;
import com.example.Project.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<RegisterResponse>register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgotPassword")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword (@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest){
        ForgotPasswordResponse response = authService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ResetPasswordResponse> resetPassword (@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        ResetPasswordResponse response = authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(response);
    }
}