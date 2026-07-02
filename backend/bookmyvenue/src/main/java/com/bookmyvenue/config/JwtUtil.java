package com.bookmyvenue.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import com.bookmyvenue.config.JwtUtil;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey"; // min 32 chars
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, Long userId, String role) {
    return Jwts.builder()
            .setSubject(email) // ✅ email as subject
            .claim("userId", userId)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
}

   public Long extractUserId(String token) {
    return getClaims(token).get("userId", Long.class);
}

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token) {
    return getClaims(token).getSubject();
}
}