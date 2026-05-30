package com.bookmyvenue.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println("👉 JWT FILTER HIT: " + request.getServletPath());

    String path = request.getServletPath();

    if (path.startsWith("/auth/")) {
        System.out.println("✔ SKIPPING JWT FILTER FOR AUTH ENDPOINT");
        filterChain.doFilter(request, response);
        return;
    }

    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
        System.out.println("✔ TOKEN FOUND");

        String token = header.substring(7);

        if (jwtUtil.isValid(token)) {
            System.out.println("✔ TOKEN VALID");

            Long userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);

            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
        } else {
            System.out.println("❌ INVALID TOKEN");
        }
    } else {
        System.out.println("⚠️ NO TOKEN PROVIDED");
    }

    filterChain.doFilter(request, response);
}
}