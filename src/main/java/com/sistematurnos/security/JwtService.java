package com.sistematurnos.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    // Genera el token con email y rol como claims
    public String generateToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Devuelve el "sub" (email) del token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Devuelve el rol embebido
    public String extractRol(String token) {
        return getClaims(token).get("rol", String.class);
    }

    // Valida el token sin contexto adicional
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // lanza excepción si está mal
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Valida token contra UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
