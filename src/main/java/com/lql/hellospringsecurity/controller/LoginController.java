package com.lql.hellospringsecurity.controller;

import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.service.JwtService;
import com.lql.hellospringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @GetMapping("/login")
    public String getLogin() {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return "login";
    }

    @GetMapping("/home")
    public String home() {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "homepage";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public CustomUser customUser(@RequestBody CustomUser user) {

        return userService.saveUser(user);
    }

    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {




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
