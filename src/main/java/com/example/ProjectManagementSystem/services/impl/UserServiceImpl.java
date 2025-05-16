package com.example.ProjectManagementSystem.services.impl;


import com.example.ProjectManagementSystem.exceptions.UserNotFoundException;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;


    @Override
    public User register(User user) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        return repo.save(user);
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
