package com.lql.hellospringsecurity.bean;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner runner(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {

            userService.saveRole(new Authority("READ"));
            userService.saveRole(new Authority("WRITE"));
            userService.saveRole(new Authority("UPDATE"));
            userService.saveRole(new Authority("DELETE"));
            userService.saveRole(new Authority("ROLE_ADMIN"));
            userService.saveRole(new Authority("ROLE_USER"));
            userService.saveRole(new Authority("ROLE_CLIENT"));


            userService.saveUser(new CustomUser("a", passwordEncoder.encode("a")));
            userService.saveUser(new CustomUser("b", passwordEncoder.encode("b")));
            userService.saveUser(new CustomUser("c", passwordEncoder.encode("c")));
            userService.saveUser(new CustomUser("super", passwordEncoder.encode("s")));


            userService.addRoleToUser("a", "ROLE_USER");
            userService.addRoleToUser("a", "READ");
            userService.addRoleToUser("b", "ROLE_CLIENT");
            userService.addRoleToUser("b", "READ");
            userService.addRoleToUser("b", "WRITE");
            userService.addRoleToUser("c", "ROLE_ADMIN");
            userService.addRoleToUser("super", "ROLE_ADMIN");
            userService.addRoleToUser("super", "ROLE_USER");
            userService.addRoleToUser("super", "ROLE_CLIENT");
            userService.addRoleToUser("super", "UPDATE");
            userService.addRoleToUser("super", "READ");
            userService.addRoleToUser("super", "WRITE");
            userService.addRoleToUser("super", "DELETE");
            
        };
    }
}
