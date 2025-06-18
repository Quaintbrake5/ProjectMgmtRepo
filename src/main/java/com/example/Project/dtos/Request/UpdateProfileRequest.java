package com.example.Project.dtos.Request;

import com.example.Project.enums.RoleName;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {
    private String name;
    @Email(message = "Invalid Email Address")
    private String email;
//    private String phoneNumber;
    private RoleName roles;

}
