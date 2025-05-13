package com.example.ProjectManagementSystem.services;


import com.example.ProjectManagementSystem.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User register(User user);

    Optional<User> findByEmail(String email);

    List<User> getAll();
    Optional<User> getSingleUser(Long id);
    void deleteUser(Long id);
    User updateUser(User user, Long id);
}
