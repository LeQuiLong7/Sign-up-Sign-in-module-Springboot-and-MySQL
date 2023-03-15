package com.lql.hellospringsecurity.auth;

import com.lql.hellospringsecurity.exception.model.RoleNotFoundException;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository
                .findById(username)
                .orElseThrow(() ->  new UsernameNotFoundException("not found username " + username));
    }

    public UserDetail createUser(String username, String password, String role) {
        UserDetail userDetail = new UserDetail(username, passwordEncoder.encode(password));

        userDetail.setAuthorities(getAuthority(role));
        return userDetail;
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
    public UserDetail saveUser(UserDetail user) {
        return userRepository.save(user);
    }

    @Transactional
    public Authority saveRole(Authority authority) { return authorityRepository.save(authority); }

    @Transactional
    public void addRoleToUser(String username, String roleName) {
        UserDetail userDetail = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(":("));
        Authority authority = authorityRepository.findAuthorityByAuthority(roleName).orElseThrow(RoleNotFoundException::new);
        userDetail.addAuthority(authority);

    }
}
