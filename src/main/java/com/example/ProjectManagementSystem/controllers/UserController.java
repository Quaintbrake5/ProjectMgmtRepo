package com.example.ProjectManagementSystem.controllers;

import com.example.ProjectManagementSystem.dtos.Requests.LoginRequest;
import com.example.ProjectManagementSystem.dtos.Requests.RegisterRequest;
import com.example.ProjectManagementSystem.models.User;
import com.example.ProjectManagementSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        User response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request) {
        User response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // add logic to get all users
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    // add logic to get user by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USER_PROFILE')")
    public ResponseEntity<Optional<User>> getSingleUser(@PathVariable Long id) {
        Optional<User> user = userService.getSingleUser(id);
        return ResponseEntity.ok(user);
    }

    // add logic to update user by id
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_USER_PROFILE')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user, id);
        return ResponseEntity.ok(updatedUser);
    }

    // add logic to delete user by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    //add logic to find user by email
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('VIEW_USER_PROFILE')")
    public ResponseEntity<Optional<User>> findByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }







}
