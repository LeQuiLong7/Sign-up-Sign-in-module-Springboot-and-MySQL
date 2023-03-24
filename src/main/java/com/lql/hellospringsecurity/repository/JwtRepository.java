package com.lql.hellospringsecurity.repository;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtRepository {

    String generateToken(Map<String, Object> claims, UserDetails user);
    String extractUsername(String token);
    Claims extractAllClaims(String token);
    <T> T extractClaims(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token, UserDetails user);

}
