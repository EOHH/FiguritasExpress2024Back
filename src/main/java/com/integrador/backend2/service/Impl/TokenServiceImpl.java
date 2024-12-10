package com.integrador.backend2.service.Impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import com.integrador.backend2.model.User;
import com.integrador.backend2.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;


import java.security.Key;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Genera una clave segura automáticamente
    private final long jwtExpirationMs = 86400000; // 1 día en milisegundos

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // Devuelve el email o username
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el token: " + e.getMessage());
        }
    }
}
