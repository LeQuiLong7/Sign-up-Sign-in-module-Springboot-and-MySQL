package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.annotation.MyAnnotation;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@MyAnnotation("Hello World")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository repository;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<CustomUser> list() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('WRITE')")
    public CustomUser add(@RequestBody CustomUser user) {

        System.out.println("Posted successfully!");
        return repository.save(user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<?> getCustomUserById(@PathVariable("username") String username) {
        Optional<CustomUser> user = repository.findByUsername(username);

        if (user.isPresent())
            return ResponseEntity.ok(user);

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public List<CustomUser> deleteById(@PathVariable String username) {
        Optional<CustomUser> user = repository.findByUsername(username);
        user.ifPresent(repository::delete);
        return repository.findAll();
    }



}
