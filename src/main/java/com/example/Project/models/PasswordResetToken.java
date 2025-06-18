package com.example.Project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The user this token belongs to */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** A 6-digit numeric code */
    @Column(name = "token", nullable = false, length = 6)
    private String token;

    /** Expiration timestamp */
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    /** Whether this token has been used */
    @Column(name = "used", nullable = false)
    private boolean used;
}
