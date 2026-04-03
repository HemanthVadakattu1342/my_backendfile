package com.eduerp.service;

import com.eduerp.dto.*;
import com.eduerp.entity.User;
import com.eduerp.repository.UserRepository;
import com.eduerp.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid email or password");
        if (user.getStatus() == User.Status.INACTIVE)
            throw new RuntimeException("Account is inactive. Contact admin.");
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, UserDto.from(user));
    }

    public AuthResponse signup(SignupRequest req) {
        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new RuntimeException("Passwords do not match");
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered");
        User.Role role;
        try {
            role = User.Role.valueOf(req.getRole().trim().toLowerCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role: " + req.getRole());
        }
        User user = User.builder()
            .name(req.getName().trim())
            .email(req.getEmail().trim().toLowerCase())
            .password(passwordEncoder.encode(req.getPassword()))
            .role(role)
            .status(User.Status.ACTIVE)
            .build();
        userRepo.save(user);
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, UserDto.from(user));
    }
}
