package com.springboot.tourism_agency_backend.services;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.models.Users;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service

public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(Users user) {
        return Jwts
            .builder()
            .subject(user.getUsername())
            .claim("dni", user.getDni())
            .claim("name", user.getName())
            .claim("surname", user.getSurname())
            .claim("role", user.getRole().getName())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(this.generateKey())
            .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(this.generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        }
        catch(ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
        }
        catch(SignatureException e) {
            System.out.println("Token is invalid: " + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return null;
    }

    public boolean verifyToken(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();

        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    private boolean isTokenExpired(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(this.generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
        }
        catch(ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
        }
        catch(SignatureException e) {
            System.out.println("Token is invalid: " + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        return true;
    }
}
