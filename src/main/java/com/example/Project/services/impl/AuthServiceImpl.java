package com.example.Project.services.impl;

import com.example.Project.dtos.Request.ForgotPasswordRequest;
import com.example.Project.dtos.Request.RegisterRequest;
import com.example.Project.dtos.Request.LoginRequest;
import com.example.Project.dtos.Request.ResetPasswordRequest;
import com.example.Project.dtos.Response.ForgotPasswordResponse;
import com.example.Project.dtos.Response.LoginResponse;
import com.example.Project.dtos.Response.RegisterResponse;
import com.example.Project.dtos.Response.ResetPasswordResponse;
import com.example.Project.enums.UserStatus;
import com.example.Project.exceptions.InvalidResetTokenException;
import com.example.Project.exceptions.UserAlreadyExistsException;
import com.example.Project.models.User;
import com.example.Project.repository.PasswordResetTokenRepository;
import com.example.Project.repository.RoleRepository;
import com.example.Project.repository.UserRepository;
import com.example.Project.security.jwt.JwtTokenProvider;
import com.example.Project.services.AuthService;
import com.example.Project.utils.PasswordUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import com.example.Project.models.PasswordResetToken;
import org.springframework.mail.javamail.JavaMailSender;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    private final PasswordResetTokenRepository tokenRepository;

    //    @Autowired PasswordUtil;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private static final int TOKEN_LENGTH = 6;
    private static final int TOKEN_EXPIRY_MINUTES = 15;
    private final JavaMailSender mailSender;
    private static final SecureRandom RANDOM = new SecureRandom();




    @Override
    public RegisterResponse register(@org.jetbrains.annotations.NotNull @Valid RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        else {
            User user = new User();
            user.setUserName(request.getName());
            user.setEmail(request.getEmail());
            user.setPasswordHash(PasswordUtil.encode(request.getPassword()));
            user.setStatus(UserStatus.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            user.setRoles(Collections.singleton(roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"))));

            userRepository.save(user);

return new RegisterResponse(user.getUserId(), user.getEmail(), "User registered successfully");        }

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

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        // 1. Lookup user (silent if not found)
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            // 2. Generate 6-digit code
            String code = String.format("%0" + TOKEN_LENGTH + "d", RANDOM.nextInt(1_000_000));

            // 3. Save token
            PasswordResetToken prt = PasswordResetToken.builder()
                    .user(user)
                    .token(code)
                    .expiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES))
                    .used(false)
                    .build();
            tokenRepository.save(prt);

            // 4. Send email
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(user.getEmail());
            msg.setSubject("Your Password Reset Code");
            msg.setText("Your password reset code is: " + code
                    + "\nThis code will expire in " + TOKEN_EXPIRY_MINUTES + " minutes.");
            mailSender.send(msg);
        });

        // 5. Always return success message to prevent account enumeration
        return new ForgotPasswordResponse(
                "If that email is registered, you will receive a reset code shortly."
        );
    }

    @Override
    public ResetPasswordResponse resetPassword(@Valid ResetPasswordRequest request) {
        // 1. Validate token
        Optional<PasswordResetToken> prt = tokenRepository.findFirstByUserEmailAndTokenOrderByExpiresAtDesc(
                        request.getEmail(),
                        request.getToken()
        );
                prt.orElseThrow(() -> new InvalidResetTokenException("Invalid or expired reset token"));
        if (prt.get().isUsed() || prt.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidResetTokenException("Invalid or expired reset token");
        }

        // 2. Update user password
        User user = prt.get().getUser();
        user.setPasswordHash(PasswordUtil.encode(request.getNewPassword()));
        userRepository.save(user);

        // 3. Mark token as used
        prt.get().setUsed(true);
        tokenRepository.save(prt.get());

        return new ResetPasswordResponse("Password reset successfully!");
    }

}

