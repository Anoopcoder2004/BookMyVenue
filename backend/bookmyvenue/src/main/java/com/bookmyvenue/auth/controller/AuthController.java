package com.bookmyvenue.auth.controller;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.auth.service.AuthService;
import com.bookmyvenue.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }


    @PostMapping("/login")
public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
    System.out.println("login api hit");
    return authService.login(request);
}

    @GetMapping("/me")
    public AuthResponse getMe(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        return new AuthResponse(null, userId, role);
    }
}