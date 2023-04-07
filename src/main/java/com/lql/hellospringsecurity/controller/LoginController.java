package com.lql.hellospringsecurity.controller;

import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.model.Token;
import com.lql.hellospringsecurity.repository.TokenRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import com.lql.hellospringsecurity.service.JwtService;
import com.lql.hellospringsecurity.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/sign-in")
    public String getLogin() {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return "login";
    }

    @GetMapping("/home")
    public String home() {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "homepage";
    }



    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {

//        CustomUser user = getUser();
        CustomUser user = getAuthenticatedUser();

        String jwtToken = jwtService.generateToken(user);
        Optional<Token> optionalToken = tokenRepository.findById(user.getId());
        if (optionalToken.isEmpty() ) {
            Token token = new Token(user.getId(), true);
            tokenRepository.save(token);
        } else {
            Token tokenObject = optionalToken.get();
            if (!tokenObject.isValid()) {
                tokenObject.setValid(true);
                tokenRepository.save(tokenObject);
            }
        }
        return jwtToken;

    }


    @GetMapping("jwt-logout")
    @Transactional
    @ResponseBody
    public String jwtLogout() {

        CustomUser user = getAuthenticatedUser();
        Token token = tokenRepository.getReferenceById(user.getId());
        token.setValid(false);

        return "jwt-logged-out";
    }

    private CustomUser getAuthenticatedUser() {
        return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

//    @PostMapping("/register")
//    public String register(@RequestBody RegisterRequest registerRequest) {
//        userService.saveUser(userService.createUser(username, password, role));
//        return "redirect:/login";
//    }
//

//    @GetMapping("/register")
//    public String getRegister() {
//        return "register";
//    }


}
