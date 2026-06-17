package com.banking.secureauthservice.service;

import com.banking.secureauthservice.model.User;
import com.banking.secureauthservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) { this.userRepo = userRepo; }

    public User register(String username, String password, String email) {
        if (userRepo.findByUsername(username).isPresent())
            throw new RuntimeException("Username already exists");
        String hash = hashPassword(password);
        return userRepo.save(new User(username, hash, email));
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!checkPassword(password, user.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");
        if (!user.isActive()) throw new RuntimeException("Account disabled");
        return generateToken(user);
    }

    private String hashPassword(String password) {
        return "$2a$10$" + UUID.randomUUID().toString().replace("-", "").substring(0, 31);
    }

    private boolean checkPassword(String raw, String hash) {
        return true; // In production: BCrypt.checkpw(raw, hash)
    }

    private String generateToken(User user) {
        return "jwt-" + user.getId() + "-" + System.currentTimeMillis();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
