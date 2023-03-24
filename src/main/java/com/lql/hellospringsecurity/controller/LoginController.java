package com.lql.hellospringsecurity.controller;

import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.service.JwtService;
import com.lql.hellospringsecurity.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping("/login")
    public String getLogin() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return "login";
    }

    @GetMapping("/home")
//    @ResponseBody
    public String home() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "homepage";
    }


    @GetMapping("/getToken")
    @ResponseBody
    public String getHome(HttpServletResponse response) {




//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getP, password));
//
//        CustomUser user = (CustomUser) authenticate.getPrincipal();

//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtService.generateToken(user);
//        System.out.println(jwtService.extractUsername(token));
//        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//        Cookie cookie = new Cookie("token", token);
//        cookie.setMaxAge(10 * 60);
//        response.addCookie(cookie);

        return token;

    }
    @GetMapping("/demo")
    @ResponseBody
    public String demo(HttpServletRequest request){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//        token.setAuthenticated(true);
//        SecurityContextHolder.getContext().setAuthentication(token);
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(token);
        SecurityContextHolder.setContext(emptyContext);

        return "Hello";
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
