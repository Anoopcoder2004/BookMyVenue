package com.bookmyvenue.auth.service;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.common.entity.User;
import com.bookmyvenue.common.enums.Role;
import com.bookmyvenue.common.response.ApiResponse;
import com.bookmyvenue.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.bookmyvenue.config.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse<AuthResponse> signup(SignupRequest request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists");
        });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .build();

        userRepository.save(user);

String token = jwtUtil.generateToken(
        user.getEmail(),   // ⭐ added
        user.getId(),
        user.getRole().name()
);
        AuthResponse authResponse = new AuthResponse(
                token,
                user.getId(),
                user.getRole().name());

        return new ApiResponse<>(
                201,
                "User registered successfully",
                authResponse);
    }
@Override
public ApiResponse<AuthResponse> login(LoginRequest request) {

    System.out.println("👉 SERVICE START: login method");

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
                System.out.println("❌ USER NOT FOUND");
                return new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                );
            });

    System.out.println("✔ USER FOUND: " + user.getEmail());

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        System.out.println("❌ PASSWORD WRONG");
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );
    }

    System.out.println("✔ PASSWORD MATCHED");

String token = jwtUtil.generateToken(
        user.getEmail(),   // ⭐ added
        user.getId(),
        user.getRole().name()
);
    System.out.println("✔ TOKEN GENERATED");

    return new ApiResponse<>(
            200,
            "Login successful",
            new AuthResponse(token, user.getId(), user.getRole().name())
    );
}
}