package com.example.ProjectManagementSystem.services.impl;


import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.exceptions.UserNotFoundException;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired private final UserRepository repo;
    @Autowired private final PasswordEncoder encoder;


@Override
public User register(@Valid RegisterRequest request) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPasswordHash(encoder.encode(request.getPasswordHash()));
    return repo.save(user);
}

    @Override
    public User login(@Valid LoginRequest request) {
        Optional<User> userOpt = repo.findByEmail(request.getEmail());
        if (userOpt.isPresent() && encoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            return userOpt.get();
        } else {
            throw new UserNotFoundException("Invalid email or password");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<User> getSingleUser(Long userId) {
        return repo.findById(userId);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if(!repo.existsById(userId)){
            throw new UserNotFoundException("Cannot delete, user not found with ID " + userId);
        }
        repo.deleteById(userId);
    }

    @Override
    @Transactional
    public User updateUser(User user, Long userId) {
        Optional<User> existingUserOpt = repo.findById(userId);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPasswordHash(encoder.encode(user.getPasswordHash()));
            return repo.save(existingUser);
        } else {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }
}
