package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.CustomUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtRepository {

    String generateToken(Map<String, Object> claims, CustomUser user);
    long extractUserId(String token);
    String extractUserName(String token);
    Claims extractAllClaims(String token);
    <T> T extractClaims(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token);

}
