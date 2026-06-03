package com.bookmyvenue.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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

        // 🔥 IMPROVED: skip more public endpoints
        if (path.startsWith("/auth/") || path.startsWith("/error")) {
            System.out.println("✔ SKIPPING JWT FILTER FOR PUBLIC ENDPOINT");
            filterChain.doFilter(request, response);
            return;
        }

        // 🔥 NEW: Get token from cookie
        String token = getTokenFromCookie(request);

        if (token != null) {
            System.out.println("✔ TOKEN FOUND (COOKIE)");

            try { // 🔥 NEW: Exception safety added

                if (jwtUtil.isValid(token)) {
                    System.out.println("✔ TOKEN VALID");

                    Long userId = jwtUtil.extractUserId(token);
                    String role = jwtUtil.extractRole(token);

                    // ❌ REMOVED: Not needed, SecurityContext is source of truth
                    // request.setAttribute("userId", userId);
                    // request.setAttribute("role", role);

                    // 🔥 Convert role → ROLE_XXX
                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    List.of(authority)
                            );

                    // 🔥 NEW: Prevent overriding existing authentication
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println("🔥 AUTH SET: " + auth.getAuthorities());
                    }

                } else {
                    System.out.println("❌ INVALID TOKEN");
                }

            } catch (Exception e) { // 🔥 NEW: handle malformed token
                System.out.println("❌ JWT ERROR: " + e.getMessage());
            }

        } else {
            System.out.println("⚠️ NO TOKEN PROVIDED (COOKIE)");
        }

        filterChain.doFilter(request, response);
    }

    // 🔥 SAME (GOOD): Extract JWT from cookie
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