package com.nikola787.budget_flow_tracker.model.authentication;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@SuperBuilder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserProfile userProfile;

    public Token(Long id, String token, LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime validatedAt, UserProfile userProfile) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.validatedAt = validatedAt;
        this.userProfile = userProfile;
    }

    public Token() {
    }

}
