package com.example.ProjectManagementSystem.services.impl;

import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.enums.UserStatus;
import com.example.ProjectManagementSystem.exceptions.UserAlreadyExistsException;
import com.example.ProjectManagementSystem.models.Role;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.RoleRepository;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.security.jwt.JwtTokenProvider;
import com.example.ProjectManagementSystem.services.AuthService;
import com.example.ProjectManagementSystem.utils.PasswordUtil;
import lombok.*;

import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Responses.LoginResponse;
import com.example.ProjectManagementSystem.dtos.Responses.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired PasswordUtil passwordUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(PasswordUtil.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(Collections.singleton(roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"))));

        userRepository.save(user);

        return new RegisterResponse(user.getUserId(), user.getName(), user.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getValidityInMilliseconds())
                .build();
    }

}

