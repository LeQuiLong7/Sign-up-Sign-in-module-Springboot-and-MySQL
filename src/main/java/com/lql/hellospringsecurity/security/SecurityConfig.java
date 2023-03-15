package com.lql.hellospringsecurity.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
//                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/static/js/**", "/css/**").permitAll()
                    .requestMatchers("/register").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin()
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true)
                        .usernameParameter("id")
                        .passwordParameter("pass")
                    .and().httpBasic()
                    .and().rememberMe()
                        .tokenValiditySeconds(5*24*60*60)
                        .key("key")
                        .rememberMeParameter("_re")
                        .rememberMeCookieName("_re")
                    .and().logout()
                        .logoutUrl("/logging-out")
                        .permitAll()
                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login")
                    .and().build();
    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder.encode("user"))
//                        .authorities(USER.getGrantedAuthorities())
//                        .build();
//        UserDetails admin =
//                User.builder()
//                        .username("admin")
//                        .password(passwordEncoder.encode("admin"))
//                        .authorities(ADMIN.getGrantedAuthorities())
//                        .build();
//        UserDetails client =
//                User.builder()
//                        .username("client")
//                        .password(passwordEncoder.encode("client"))
//                        .authorities(CLIENT.getGrantedAuthorities())
//                        .build();
//
//
//        return new InMemoryUserDetailsManager(user, admin, client);
//    }


}
