package com.lql.hellospringsecurity.controller;


import com.lql.hellospringsecurity.annotation.MyAnnotation;
import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.exception.model.RoleNotFoundException;
import com.lql.hellospringsecurity.model.UserDTO;
import com.lql.hellospringsecurity.model.request.PageRequestDetail;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final AuthorityRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public List<UserDTO> getAll(@RequestParam(required = false, defaultValue = "0") int pageNo,
                             @RequestParam(required = false, defaultValue = "3") int pageSize,
                             @RequestParam(required = false, defaultValue = "id") String sortBy) {

        Pageable pageable  = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CustomUser> users = userRepository.findAll(pageable);
        return users.getContent().stream().map(CustomUser::mapToUserDTO).toList();
    }

//    @PostMapping
//    @Transactional
//    @PreAuthorize("hasAuthority('WRITE')")
//    public UserDTO add(@RequestBody CustomUser user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return CustomUser.mapToUserDTO(this.userRepository.save(user));
//    }
//

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        Optional<CustomUser> user = this.userRepository.findByUsername(username);

        if (user.isPresent())
            return ResponseEntity.ok(CustomUser.mapToUserDTO(user.get()));

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public List<UserDTO> deleteByUsername(@PathVariable String username,
                                    @RequestParam(required = false, defaultValue = "0") int pageNo,
                                    @RequestParam(required = false, defaultValue = "3") int pageSize,
                                    @RequestParam(required = false, defaultValue = "id") String sortBy) {

        Optional<CustomUser> user = userRepository.findByUsername(username);
        user.ifPresent(userRepository::delete);
        Pageable pageable  = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return this.userRepository.findAll(pageable).getContent().stream().map(CustomUser::mapToUserDTO).toList();
    }

    @PutMapping("/addRole")
    @PreAuthorize("hasRole('OWNER')")
    @Transactional
    public UserDTO addRoleToUser(String username, String roleName){
        CustomUser user = userRepository.findByUsername(username).orElseThrow(MyUsernameNotFoundException::new);
        Authority role = roleRepository.findAuthorityByAuthority(roleName.toUpperCase()).orElseThrow(RoleNotFoundException::new);
        user.addAuthority(role);
        return CustomUser.mapToUserDTO(user);
    }


}
