package com.example.Project.controllers;

import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.UpdateProfileRequest;
import com.example.Project.models.User;
import com.example.Project.services.UserService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        User response = userService.register(request);
        return ResponseEntity.ok("User registered successfully: " + response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        User response = userService.login(request);
        return ResponseEntity.ok("User logged in successfully: " + response);
    }

    // add logic to get all users
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS') + \"hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    public static class OptionalUserResponse {
        public boolean present;
        public User get;

        public OptionalUserResponse(String string, Optional<User> user) {
            this.present = user.isPresent();
            this.get = user.orElse(null);
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_USER_PROFILE') + \"hasRole('ADMIN')")
    public ResponseEntity<OptionalUserResponse> getSingleUser(@PathVariable Long id) {
        Optional<User> user = userService.getSingleUser(id);
        return ResponseEntity.ok(new OptionalUserResponse("User with ID " + id + " found!", user));
    }

    // add logic to update user by id
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_USER_PROFILE') + \"hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user, id);
        return ResponseEntity.ok("User updated successfully: " + updatedUser + "!");
    }

    // add logic to delete user by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER') + \"hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    //add logic to find user by email
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('VIEW_USER_PROFILE') + \"hasRole('ADMIN')")
    public ResponseEntity<OptionalUserResponse> findByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return ResponseEntity.ok(new OptionalUserResponse("User with email " + email + " found!", user));
    }

    @PostMapping("/assign-project/{userId}/{projectId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')" + "hasRole('ADMIN')")
    public ResponseEntity<String> assignProjectToUser(@PathVariable Long userId, @PathVariable Long projectId) {
        userService.assignProjectToUser(userId, projectId);
        return ResponseEntity.ok("Project assigned to user successfully!");
    }

    @DeleteMapping("/remove-project/{userId}/{projectId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS)" + "hasRole('ADMIN)")
    public ResponseEntity<String> removeProjectFromUser(@PathVariable Long userId, @PathVariable Long projectId) {
        userService.removeProjectFromUser(userId, projectId);
        return ResponseEntity.ok("Project assigned to user has been removed successfully!");
    }

    @PostMapping("/assign-task/{userId}/{taskId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')" + "hasRole('ADMIN')")
    public ResponseEntity<String> assignTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        userService.assignTaskToUser(userId, taskId);
        return ResponseEntity.ok("Task assigned to user successfully!");
    }

    @DeleteMapping("/delete-task/{userId}/{taskId}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')" + "hasRole('ADMIN')" )
    public ResponseEntity<String> removeTaskFromUser(@PathVariable Long userId, @PathVariable Long taskId) {
        userService.removeTaskFromUser(userId, taskId);
        return ResponseEntity.ok("Task assigned to user has been deleted successfully!");
    }

//    @PostMapping("/assign-role/{userId}/{roleId}")
//    @PreAuthorize("hasAuthority('MANAGE_USERS')" + "hasRole('ADMIN')")
//    public ResponseEntity<String> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
//        userService.assignRoleToUser(userId, roleId);
//        return ResponseEntity.ok("Role assigned to user successfully");
//    }

//    @PostMapping("/assign-permission/{userId}/{permissionId}")
//    @PreAuthorize("hasAuthority('MANAGE_USERS')" + "hasRole('ADMIN')")
//    public ResponseEntity<String> assignPermissionToUser(@PathVariable Long userId, @PathVariable Long permissionId) {
//        userService.assignPermissionToUser(userId, permissionId);
//        return ResponseEntity.ok("Permission assigned to user successfully");
//    }

    @PatchMapping("/updateProfile")
    @PreAuthorize("isAuthenticated() and hasAuthority('EDIT_USER_PROFILE" + " or hasRole('ADMIN')" + " or hasRole('USER')")
    public ResponseEntity<User> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest updateProfileRequest
    ) {
        User updated = userService.updateProfile(
                userDetails.getUsername(), updateProfileRequest
        );
        return ResponseEntity.ok(updated);
    }


}
