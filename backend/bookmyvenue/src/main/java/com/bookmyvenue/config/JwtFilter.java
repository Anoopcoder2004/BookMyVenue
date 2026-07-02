package com.bookmyvenue.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority; ❌ ⭐ REMOVED
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// import java.util.List; ❌ ⭐ REMOVED

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userService; // ⭐ ALREADY CORRECT

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("👉 JWT FILTER HIT: " + request.getServletPath());

        String path = request.getServletPath();

        if (path.startsWith("/auth/") ||
                path.startsWith("/uploads") ||
                path.startsWith("/error") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui")) {

            System.out.println("✔ SKIPPING JWT FILTER FOR PUBLIC ENDPOINT");
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromHeader(request);

        if (token == null) {
            token = getTokenFromCookie(request);
        }

        if (token != null) {
            System.out.println("✔ TOKEN FOUND");

            try {
                if (jwtUtil.isValid(token)) {
                    System.out.println("✔ TOKEN VALID");

                    // ⭐ NEW: Extract username (email)
                    String username = jwtUtil.extractUsername(token);

                    // ⭐ NEW: Load full user details from DB
                    CustomUserDetails userDetails =
                            (CustomUserDetails) userService.loadUserByUsername(username);

                    // ⭐ NEW: Create auth using userDetails (NOT userId)
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // ⭐ SAME CHECK (kept)
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        // ⭐ UPDATED LOG
                        System.out.println("🔥 AUTH SET FOR USER: " + userDetails.getUsername());
                    }

                } else {
                    System.out.println("❌ INVALID TOKEN");
                }

            } catch (Exception e) {
                System.out.println("❌ JWT ERROR: " + e.getMessage());
            }

        } else {
            System.out.println("⚠️ NO TOKEN PROVIDED");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;

        for (Cookie c : request.getCookies()) {
            if ("jwt".equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}