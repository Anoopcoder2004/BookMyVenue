package com.bookmyvenue.auth.controller;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public AuthResponse getMe(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        return new AuthResponse(null, userId, role);
    }
}