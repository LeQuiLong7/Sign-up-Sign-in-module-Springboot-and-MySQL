package com.lql.hellospringsecurity.controller;

import com.lql.hellospringsecurity.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String getHome() {
        return "homepage";
    }

    @PostMapping("/register")
    public String register(String username, String password, String role) {
        userService.saveUser(userService.createUser(username, password, role));
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }


}
