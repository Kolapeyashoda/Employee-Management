package com.emp.Employee_Management.jwt;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenProvider {

    private static final String SECRET_KEY = "your-secret-key";
    private static final long EXPIRATION_TIME = 86400000L; // 1 day

    // Generate JWT Token
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get Authentication from token
    public Authentication getAuthentication(String token) {
        String userName = Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                .build().parseClaimsJws(token)
                .getBody()
                .getSubject();

        User user = new User(userName, "", new ArrayList<>()); // Empty roles for now
        return new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>());
    }
}
