package com.bookmyvenue.auth.controller;

import com.bookmyvenue.auth.dto.*;
import com.bookmyvenue.auth.service.AuthService;
import com.bookmyvenue.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }


//     @PostMapping("/login")
// public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
//     System.out.println("login api hit");
//     return authService.login(request);
// }


    /* 🔥 UPDATED LOGIN METHOD */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

        System.out.println("login api hit");

        /* ✅ CALL SERVICE */
        ApiResponse<AuthResponse> response = authService.login(request);

        /* ✅ EXTRACT TOKEN (you must return token from service temporarily) */
        String token = response.getData().getToken();

        /* 🔥 CREATE COOKIE */
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)   // ✅ prevents JS access
                .secure(false)    // ⚠️ true in production (HTTPS)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        /* ❌ REMOVE TOKEN FROM RESPONSE BODY (important for security) */
        response.getData().setToken(null);

        /* ✅ RETURN COOKIE IN HEADER */
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @GetMapping("/me")
    public AuthResponse getMe(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        return new AuthResponse(null, userId, role);
    }
     /* 🆕 OPTIONAL: LOGOUT ENDPOINT */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // true in production
                .path("/")
                .maxAge(0) // ✅ delete cookie
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out");
    }
}