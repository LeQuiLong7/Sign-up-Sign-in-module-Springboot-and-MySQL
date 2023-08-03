package com.lql.hellospringsecurity.bean;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import com.lql.hellospringsecurity.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    private static SecretKey getSecretKey() {
        final String SECRET_KEY = "7336763979244226452948404D6351665468576D5A7134743777217A25432A46";
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }


    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder().signWith(getSecretKey(), SignatureAlgorithm.HS256);

    }
    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build();

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


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("namkhuc6@gmail.com");
        mailSender.setPassword("ijieaobnnrwmqbuj");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Bean
//    @Transactional
    public CommandLineRunner runner(PasswordEncoder passwordEncoder) {
        return args -> {
//            Authority read = authorityRepository.saveAndFlush(new Authority("READ"));
//            Authority write = authorityRepository.saveAndFlush(new Authority("WRITE"));
//            Authority update = authorityRepository.saveAndFlush(new Authority("UPDATE"));
//            Authority delete = authorityRepository.saveAndFlush(new Authority("DELETE"));
//            Authority roleAdmin = authorityRepository.saveAndFlush(new Authority("ROLE_ADMIN"));
//            Authority roleUser = authorityRepository.saveAndFlush(new Authority("ROLE_USER"));
//            Authority roleOwner = authorityRepository.saveAndFlush(new Authority("ROLE_OWNER"));

            Authority read = authorityRepository.getAuthorityByAuthority("READ");
            Authority write = authorityRepository.getAuthorityByAuthority("WRITE");
            Authority update = authorityRepository.getAuthorityByAuthority("UPDATE");
            Authority delete = authorityRepository.getAuthorityByAuthority("DELETE");
            Authority roleAdmin = authorityRepository.getAuthorityByAuthority("ROLE_ADMIN");
            Authority roleUser = authorityRepository.getAuthorityByAuthority("ROLE_USER");
            Authority roleOwner = authorityRepository.getAuthorityByAuthority("ROLE_OWNER");
//            CustomUser user = new CustomUser("user", passwordEncoder.encode("u"), true);
////            CustomUser user = userRepository.saveAndFlush(new CustomUser("user", passwordEncoder.encode("u"), true));
////            CustomUser user1 = userRepository.saveAndFlush(user);
//            userRepository.save(user);
//            CustomUser user1 = userRepository.getByUsername("user");

//            CustomUser user = userRepository.saveAndFlush(new CustomUser("user", passwordEncoder.encode("u"), true));
//            userRepository.saveAndFlush(new CustomUser("admin", passwordEncoder.encode("a"), true));
//            userRepository.saveAndFlush(new CustomUser("owner", passwordEncoder.encode("o"), true));

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
