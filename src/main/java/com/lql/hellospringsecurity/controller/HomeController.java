package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.annotation.MyAnnotation;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.model.Person;
import com.lql.hellospringsecurity.repository.PersonRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@MyAnnotation("Hello World")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    private final PersonRepository repositories;
    private final UserRepository repository;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<CustomUser> list() {
        return repository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public Person add(@RequestBody Person person) {

        System.out.println("Posted successfully!");
        return repositories.add(person);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public Person getPersonById(@PathVariable("id") int id) {
        return repositories.get(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> deleteById(@PathVariable int id) {
        if (repositories.deleteById(id))
            System.out.println("Delete successfully");
        return repositories.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Person replace(@PathVariable int id, @RequestBody Person newPerson) {
        return repositories.replace(id, newPerson);
    }


}
