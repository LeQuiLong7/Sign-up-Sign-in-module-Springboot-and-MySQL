package com.lql.hellospringsecurity.service;


import com.lql.hellospringsecurity.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService implements JwtRepository {
    private final String SECRET_KEY = "7336763979244226452948404D6351665468576D5A7134743777217A25432A46";

    private Key secretKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String generateToken(Map<String, Object> claims, UserDetails user) {
        String compact = Jwts.builder().signWith(secretKey(), SignatureAlgorithm.HS256)
                .addClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60
                        * 1000)))
                .compact();
        return compact;
    }

    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        return (extractUsername(token).equals(user.getUsername())) && isTokenNotExpired(token);
    }

    private boolean isTokenNotExpired(String token) {
        return extractClaims(token, Claims::getExpiration).after(new Date(System.currentTimeMillis()));
    }
}