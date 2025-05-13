package com.example.ProjectManagementSystem.services.impl;


import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.repositories.UserRepository;
import com.example.ProjectManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;


    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
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
    public Optional<User> getSingleUser(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> existingUserOpt = repo.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(encoder.encode(user.getPassword()));
            }
            // Update other fields as needed
            return repo.save(existingUser);
        } else {
            throw new NoSuchElementException("User not found with id: " + id);
        }
    }
}
