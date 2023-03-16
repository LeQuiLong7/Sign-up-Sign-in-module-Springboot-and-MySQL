package com.lql.hellospringsecurity;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.auth.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HelloSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringSecurityApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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


            userService.saveUser(new CustomUser("a", passwordEncoder().encode("a")));
            userService.saveUser(new CustomUser("b", passwordEncoder().encode("b")));
            userService.saveUser(new CustomUser("c", passwordEncoder().encode("c")));


            userService.addRoleToUser("a", "ROLE_USER");
            userService.addRoleToUser("a", "READ");
            userService.addRoleToUser("b", "ROLE_CLIENT");
            userService.addRoleToUser("b", "READ");
            userService.addRoleToUser("b", "WRITE");
            userService.addRoleToUser("c", "ROLE_ADMIN");
        };
    }
}
