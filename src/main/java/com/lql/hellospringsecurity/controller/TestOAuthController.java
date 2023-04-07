package com.lql.hellospringsecurity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class TestOAuthController {

    @GetMapping
    public String hello() {
        return "Hello Spring OAuth";
    }
}
