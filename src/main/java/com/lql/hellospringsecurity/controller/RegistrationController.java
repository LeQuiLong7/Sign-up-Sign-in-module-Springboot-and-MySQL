package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.ConfirmationTokenExpiredException;
import com.lql.hellospringsecurity.model.UserDTO;
import com.lql.hellospringsecurity.model.request.RegistryRequest;
import com.lql.hellospringsecurity.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registry")
@RequiredArgsConstructor
public class RegistrationController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<UserDTO> registry(@RequestBody RegistryRequest registryRequest) {

        CustomUser user = mailService.registryNewUser(registryRequest);

        return ResponseEntity.status(201).body(CustomUser.mapToUserDTO(user));
    }

    @GetMapping("/confirmEmail/token={token}")
    public UserDTO confirmEmail(@PathVariable String token) {
        if(mailService.isTokenValid(token))
            return CustomUser.mapToUserDTO(mailService.updateUserState(token));

        throw new ConfirmationTokenExpiredException();
    }


}
