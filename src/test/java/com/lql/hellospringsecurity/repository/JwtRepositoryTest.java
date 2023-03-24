package com.lql.hellospringsecurity.repository;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;


//@SpringBootTest
class JwtRepositoryTest {


    @Test
    void name() {
        String SECRET_KEY = "6250655368566D597133743677397A24432646294A404E635166546A576E5A72";
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

        System.out.println(secretKey.getAlgorithm());
        System.out.println(secretKey.getFormat());
//
//        JwtParser build = Jwts.parserBuilder().setSigningKey(secretKey)
//                .build();


    }
}