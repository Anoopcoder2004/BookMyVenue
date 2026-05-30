package com.bookmyvenue.auth.service;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.common.entity.User;
import com.bookmyvenue.common.enums.Role;
import com.bookmyvenue.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookmyvenue.config.JwtUtil;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse signup(SignupRequest request) {

        // check if user exists
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email already exists");
        });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .build();

        userRepository.save(user);

String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

return new AuthResponse(token, user.getId(), user.getRole().name());    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

return new AuthResponse(token, user.getId(), user.getRole().name());
    }
}