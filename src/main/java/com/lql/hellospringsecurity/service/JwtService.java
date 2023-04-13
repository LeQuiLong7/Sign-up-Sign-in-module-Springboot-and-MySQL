package com.lql.hellospringsecurity.service;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.model.Token;
import com.lql.hellospringsecurity.repository.JwtRepository;
import com.lql.hellospringsecurity.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtRepository {

    private final TokenRepository tokenRepository;
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    @Override
    public long extractUserId(String token) {
        return  Long.parseLong(extractClaims(token, Claims::getId));
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    @Override
    public Claims extractAllClaims(String token) {
        return  jwtParser
                .parseClaimsJws(token)
                .getBody();
    }


    public String generateToken(Map<String, Object> claims, CustomUser user) {

        return  jwtBuilder
                .addClaims(claims)
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60
                        * 1000)))
                .compact();
    }

    public String generateToken(CustomUser user) {
        return generateToken(new HashMap<>(), user);
    }

    public boolean isTokenValid(String token) {
        Token tokenObject = tokenRepository.findById(extractUserId(token)).orElseThrow(MyUsernameNotFoundException::new);
        return isTokenNotExpired(token) && tokenObject.isValid();


    }
    private boolean isTokenNotExpired(String token) {
        return extractClaims(token, Claims::getExpiration).after(new Date(System.currentTimeMillis()));
    }
}