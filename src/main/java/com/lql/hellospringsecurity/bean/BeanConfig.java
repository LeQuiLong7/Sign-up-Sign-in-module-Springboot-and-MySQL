package com.lql.hellospringsecurity.bean;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
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

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;



    @Bean
    public JwtBuilder jwtBuilder() {
        final String SECRET_KEY = "7336763979244226452948404D6351665468576D5A7134743777217A25432A46";
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

        return Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256);

    }
    @Bean
    public JwtParser jwtParser() {
        final String SECRET_KEY = "7336763979244226452948404D6351665468576D5A7134743777217A25432A46";
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

        return Jwts.parserBuilder().setSigningKey(secretKey).build();

    }


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

//    @Bean
//    @Transactional
    public CommandLineRunner runner(PasswordEncoder passwordEncoder) {
        return args -> {
            authorityRepository.save(new Authority("READ"));
            authorityRepository.save(new Authority("WRITE"));
            authorityRepository.save(new Authority("UPDATE"));
            authorityRepository.save(new Authority("DELETE"));
            authorityRepository.save(new Authority("ROLE_ADMIN"));
            authorityRepository.save(new Authority("ROLE_USER"));
            authorityRepository.save(new Authority("ROLE_OWNER"));

            Authority read = authorityRepository.getAuthorityByAuthority("READ");
            Authority write = authorityRepository.getAuthorityByAuthority("WRITE");
            Authority update = authorityRepository.getAuthorityByAuthority("UPDATE");
            Authority delete = authorityRepository.getAuthorityByAuthority("DELETE");
            Authority roleAdmin = authorityRepository.getAuthorityByAuthority("ROLE_ADMIN");
            Authority roleUser = authorityRepository.getAuthorityByAuthority("ROLE_USER");
            Authority roleOwner = authorityRepository.getAuthorityByAuthority("ROLE_OWNER");

            userRepository.save(new CustomUser("user", passwordEncoder.encode("u")));
            userRepository.save(new CustomUser("admin", passwordEncoder.encode("a")));
            userRepository.save(new CustomUser("owner", passwordEncoder.encode("o")));

            CustomUser user = userRepository.getByUsername("user");
            CustomUser admin = userRepository.getByUsername("admin");
            CustomUser owner = userRepository.getByUsername("owner");

            user.addAuthority(read, roleUser);
            admin.addAuthority(read, write, update, delete, roleAdmin);
            owner.addAuthority(read, write, update, delete, roleAdmin, roleUser, roleOwner);

            userRepository.save(user);
            userRepository.save(admin);
            userRepository.save(owner);
        };
    }
}
