package com.bookmyvenue.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 🔥 ADDED
import org.springframework.security.core.authority.SimpleGrantedAuthority; // 🔥 ADDED
import org.springframework.security.core.context.SecurityContextHolder; // 🔥 ADDED
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List; // 🔥 ADDED
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

        /* ❌ OLD (REMOVE THIS) */
        // String header = request.getHeader("Authorization");

        /* 🔥 NEW (GET TOKEN FROM COOKIE) */
        String token = getTokenFromCookie(request);

        /* ❌ OLD */
        // if (header != null && header.startsWith("Bearer ")) {

        if (token != null) {  // ✅ NEW
            System.out.println("✔ TOKEN FOUND (COOKIE)");

            /* ❌ OLD */
            // String token = header.substring(7);

            if (jwtUtil.isValid(token)) {
                System.out.println("✔ TOKEN VALID");

                Long userId = jwtUtil.extractUserId(token);
                String role = jwtUtil.extractRole(token);

                request.setAttribute("userId", userId);
                request.setAttribute("role", role);

                // convert role → ROLE_USER format
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(authority)
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("🔥 AUTH SET: " + auth.getAuthorities());

            } else {
                System.out.println("❌ INVALID TOKEN");
            }
        } else {
            System.out.println("⚠️ NO TOKEN PROVIDED (COOKIE)");
        }

        filterChain.doFilter(request, response);
    }

    /* 🆕 ADD THIS METHOD */
    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie c : request.getCookies()) {
            if ("jwt".equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }
}