package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.repository.UserRepository;
import com.lql.hellospringsecurity.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final UserRepository userRepository;
    private final AvatarService avatarService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CustomUser> user = userRepository.findByUsername(username);

        return avatarService.uploadFile(file, user.get());
    }


    @GetMapping("/get-avt")
    public ResponseEntity<?> getAvatar() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        byte[] image = avatarService.downloadImageByUserId(user.getId());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }


}
