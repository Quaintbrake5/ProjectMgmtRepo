package com.example.ProjectManagementSystem.models;

import com.example.ProjectManagementSystem.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")



public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Getter
    @Column(unique = true, nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String passwordHash;

    @Setter
    @Column(nullable = false)
    private String role;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    void prePersist() {
        this.status = UserStatus.ACTIVE;
    }
    @PreUpdate
    void preUpdate() {
        this.status = UserStatus.ACTIVE;
    }

    //    public String getRole() {
//        return role;
//    }

    public void setCreatedAt(LocalDateTime ignoredNow) {
    }
}
