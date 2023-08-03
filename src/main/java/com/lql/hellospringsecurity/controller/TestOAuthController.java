package com.lql.hellospringsecurity.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class TestOAuthController {

    @GetMapping
    public String hello(OAuth2AuthenticationToken token) {
        System.out.println(token.toString());
        System.out.println(token.getCredentials());
        System.out.println(token.getPrincipal().getAttributes().toString());
        return "Hello Spring OAuth";
    }
}
