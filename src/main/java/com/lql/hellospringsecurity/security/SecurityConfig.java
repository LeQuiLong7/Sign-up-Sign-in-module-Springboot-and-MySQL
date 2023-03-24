package com.lql.hellospringsecurity.security;


import com.lql.hellospringsecurity.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
//                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                        .requestMatchers("/static/js/**", "/css/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/sign-up").permitAll()
                        .requestMatchers("/demo").permitAll()
                        .anyRequest().authenticated()
                    .and().sessionManagement()
//                        .sessionCreationPolicy(STATELESS)
                    .and().authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                    .and()
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true)
                        .usernameParameter("id")
                        .passwordParameter("pass")
                    .and()
                .httpBasic()
////                    .and().addFilter(new CustomAuthenticationFilter(authenticationManager))
                    .and()
                    .rememberMe()
                        .tokenValiditySeconds(5*24*60*60)
                        .key("key")
                        .rememberMeParameter("_re")
                        .rememberMeCookieName("_re")
                    .and().logout()
                        .logoutUrl("/logging-out")
                        .permitAll()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login")
                    .and().build();
    }


}
