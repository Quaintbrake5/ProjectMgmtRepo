package com.example.Project.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

 private Long userId;
 private String message;

 public RegisterResponse(Long userId, String email, String message) {
     this.userId = userId;
     this.message = String.format("User with email %s registered successfully with ID %d", email, userId);
 }
}
