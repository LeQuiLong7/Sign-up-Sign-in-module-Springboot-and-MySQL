package com.lql.hellospringsecurity.service;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.exception.model.RoleNotFoundException;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(MyUsernameNotFoundException::new);
        return user;
    }

    public CustomUser createUser(String username, String password, String role) {
        CustomUser user = new CustomUser(username, passwordEncoder.encode(password));

        user.setAuthorities(getAuthority(role));
        return user;
    }

    private Set<Authority> getAuthority(String role) {

        List<Authority> all = authorityRepository.findAll();
        Set<Authority> user = all
                .stream()
                .filter(authority -> authority.getAuthority().equals("READ") || authority.getAuthority().equals("ROLE_USER"))
                .collect(Collectors.toSet());
       return switch (role) {
            case "CLIENT" -> all
                    .stream()
                    .filter(
                            authority -> authority.getAuthority().equals("READ")
                            || authority.getAuthority().equals("WRITE")
                            || authority.getAuthority().equals("ROLE_USER"))
                    .collect(Collectors.toSet());
            case "ADMIN" -> all
                                .stream()
                                .filter(
                                        authority -> !authority.getAuthority().equals("ROLE_USER")
                                        && !authority.getAuthority().equals("ROLE_CLIENT")
                                ).collect(Collectors.toSet());
            default -> user;
        };
    }

    @Transactional
    public CustomUser saveUser(CustomUser user) {
        return userRepository.save(user);
    }

    @Transactional
    public Authority saveRole(Authority authority) { return authorityRepository.save(authority); }

    @Transactional
    public void addRoleToUser(String username, String roleName) {
        CustomUser user = userRepository.findByUsername(username).orElseThrow(MyUsernameNotFoundException::new);
        Authority authority = authorityRepository.findAuthorityByAuthority(roleName).orElseThrow(RoleNotFoundException::new);
        user.addAuthority(authority);

    }
}
