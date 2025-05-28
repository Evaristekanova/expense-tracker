package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt( new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public String getUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = getUsername(token);
        return (extractedUsername.equals(username) && isTokenExpired(token));
    }

}
