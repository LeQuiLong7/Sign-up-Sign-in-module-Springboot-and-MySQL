package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.annotation.MyAnnotation;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.model.PageRequestDetail;
import com.lql.hellospringsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final int DEFAULT_PAGE_SIZE = 3;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<CustomUser> get(@RequestBody PageRequestDetail pageRequestDetail) {
        Pageable pageable = PageRequest.of(pageRequestDetail.getPageNo(),
                pageRequestDetail.getPageSize() == 0 ? DEFAULT_PAGE_SIZE : pageRequestDetail.getPageSize());
        Page<CustomUser> users = repository.findAll(pageable);

        return users.getContent();

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
    public List<CustomUser> deleteById(@PathVariable String username, @RequestBody PageRequestDetail pageRequestDetail) {
        Optional<CustomUser> user = repository.findByUsername(username);
        user.ifPresent(repository::delete);
        Pageable pageable = PageRequest.of(pageRequestDetail.getPageNo(),
                pageRequestDetail.getPageSize() == 0 ? DEFAULT_PAGE_SIZE : pageRequestDetail.getPageSize());
        Page<CustomUser> users = repository.findAll(pageable);

        return users.getContent();
    }



}
