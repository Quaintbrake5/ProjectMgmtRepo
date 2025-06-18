package com.example.Project.services.impl;


import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.UpdateProfileRequest;
import com.example.Project.enums.UserStatus;
import com.example.Project.exceptions.UserNotFoundException;
import com.example.Project.models.Project;
import com.example.Project.models.Role;
import com.example.Project.models.User;
import com.example.Project.repository.UserRepository;
import com.example.Project.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor(force = true)
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
     private final UserRepository repo;
     private final PasswordEncoder encoder;
     private final com.example.Project.repository.ProjectRepository projectRepository;


@Override
public User register(@Valid RegisterRequest request) {
    User user = new User();
    user.setUserName(request.getName());
    user.setEmail(request.getEmail());
    user.setPasswordHash(encoder.encode(request.getPassword()));
    user.setStatus(UserStatus.ACTIVE); // Set default status to ACTIVE
    user.setCreatedAt(java.time.LocalDateTime.now());
    user.setUpdatedAt(java.time.LocalDateTime.now());

    // Assign default role
    Role defaultRole = new Role();
    defaultRole.setName("ROLE_ADMIN");
    defaultRole.setDescription("Default admin role");
    user.setRoles(Set.of(defaultRole));
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
            existingUser.setUserName(user.getUserName());
            existingUser.setEmail(user.getEmail());
            // Only update password if provided
            if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
                existingUser.setPasswordHash(encoder.encode(user.getPasswordHash()));
            }
            return repo.save(existingUser);
        } else {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }

    @Override
    public void assignProjectToUser(Long userId, Long projectId) {
        Optional<User> userOpt = repo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new UserNotFoundException("Project not found with ID " + projectId));
            user.getProjects().add(project);
            project.getUsers().add(user);
            repo.save(user);


        } else {
            throw new UserNotFoundException("User not found with ID " + userId);
        }
    }

    @Override
    public void removeProjectFromUser(Long userId, Long projectId) {

    }

    @Override
    public User updateProfile(String email, UpdateProfileRequest updatedInfo) {
        User user =repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        user.setUserName(updatedInfo.getName());
        user.setEmail(updatedInfo.getEmail());

        user.setUpdatedAt(java.time.LocalDateTime.now());

        User updatedUser = repo.save(user);

        return repo.save(updatedUser);
    }

    @Override
    public void assignTaskToUser(Long userId, Long TaskId) {
        Optional<User> userOpt = repo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Project project = projectRepository.findById(TaskId)
                    .orElseThrow(() -> new UserNotFoundException("Project not found with ID " + TaskId));
            user.getProjects().add(project);
            project.getUsers().add(user);
            repo.save(user);
        } else {
            throw new UserNotFoundException("User not found with ID " + userId);
        }
    }

    @Override
    public void removeTaskFromUser(Long userId, Long TaskId) {
        Optional<User> userOpt = repo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Project project = projectRepository.findById(TaskId)
                    .orElseThrow(() -> new UserNotFoundException("Project not found with ID " + TaskId));
            user.getProjects().remove(project);
            project.getUsers().remove(user);
            repo.save(user);
        } else {
            throw new UserNotFoundException("User not found with ID " + userId);
        }
    }
}
