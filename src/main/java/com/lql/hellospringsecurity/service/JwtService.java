package com.lql.hellospringsecurity.service;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.model.Token;
import com.lql.hellospringsecurity.repository.JwtRepository;
import com.lql.hellospringsecurity.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService implements JwtRepository {
    private final String SECRET_KEY = "7336763979244226452948404D6351665468576D5A7134743777217A25432A46";

    private final TokenRepository tokenRepository;

    private Key secretKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }


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
        return Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String generateToken(Map<String, Object> claims, CustomUser user) {
        String compact = Jwts.builder().signWith(secretKey(), SignatureAlgorithm.HS256)
                .addClaims(claims)
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60
                        * 1000)))
                .compact();
        return compact;
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