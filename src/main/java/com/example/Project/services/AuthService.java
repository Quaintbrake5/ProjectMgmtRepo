package com.example.Project.services;


import com.example.Project.dtos.Request.ForgotPasswordRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.ResetPasswordRequest;
import com.example.Project.dtos.Response.ForgotPasswordResponse;
import com.example.Project.dtos.Response.LoginResponse;
import com.example.Project.dtos.Response.RegisterResponse;
import com.example.Project.dtos.Response.ResetPasswordResponse;
import jakarta.validation.Valid;

public interface AuthService {
    RegisterResponse register(@Valid RegisterRequest request);

    LoginResponse login(LoginRequest request);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);

    ResetPasswordResponse resetPassword(@Valid ResetPasswordRequest request);
}
