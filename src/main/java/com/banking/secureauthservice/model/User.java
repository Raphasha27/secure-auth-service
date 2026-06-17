package com.banking.secureauthservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true) private String username;
    @Column(nullable = false) private String passwordHash;
    @Column(nullable = false, unique = true) private String email;
    private String role = "USER";
    private boolean mfaEnabled = false;
    private String mfaSecret;
    private boolean active = true;
    private LocalDateTime createdAt;

    public User() {}
    public User(String username, String passwordHash, String email) {
        this.username = username; this.passwordHash = passwordHash; this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isMfaEnabled() { return mfaEnabled; }
    public String getMfaSecret() { return mfaSecret; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setRole(String r) { this.role = r; }
    public void setMfaEnabled(boolean m) { this.mfaEnabled = m; }
    public void setMfaSecret(String s) { this.mfaSecret = s; }
    public void setActive(boolean a) { this.active = a; }
}
