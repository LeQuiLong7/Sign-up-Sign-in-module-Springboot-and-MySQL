package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.ConfirmationTokenExpiredException;
import com.lql.hellospringsecurity.model.UserDTO;
import com.lql.hellospringsecurity.model.request.RegistryRequest;
import com.lql.hellospringsecurity.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registry")
@RequiredArgsConstructor
public class RegistrationController {

    private final MailService mailService;

    @PostMapping
    public String registry(@RequestBody RegistryRequest registryRequest) {

        mailService.registryNewUser(registryRequest);

        return "Registry Successfully!";
    }

    @GetMapping("/confirmEmail/token={token}")
    public UserDTO confirmEmail(@PathVariable String token) {
        if(mailService.isTokenValid(token))
            return CustomUser.mapToUserDTO(mailService.updateUserState(token));

        throw new ConfirmationTokenExpiredException();
    }


}
