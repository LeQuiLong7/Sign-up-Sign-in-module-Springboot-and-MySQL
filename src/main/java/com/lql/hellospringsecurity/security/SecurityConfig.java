package com.lql.hellospringsecurity.security;


import com.lql.hellospringsecurity.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                    .csrf().disable()
                    .authorizeHttpRequests()
                        .requestMatchers("/static/js/**", "/css/**").permitAll()
                        .requestMatchers("/sign-in").permitAll()
                        .requestMatchers("/sign-up").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/registry/**").permitAll()
                        .requestMatchers("/exception/**").permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/api/oauth")
                    .and().authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                    .formLogin()
//                        .loginPage("/login").permitAll()
////                        .defaultSuccessUrl("/home", true)
//                        .usernameParameter("id")
//                        .passwordParameter("pass")
//                    .and()
                    .httpBasic()
//                    .and()
//                    .rememberMe()
//                        .tokenValiditySeconds(5*24*60*60)
//                        .key("key")
//                        .rememberMeParameter("_re")
//                        .rememberMeCookieName("_re")
//                    .and().logout()
//                        .logoutUrl("/logging-out")
//                        .permitAll()
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID", "remember-me")
//                        .logoutSuccessUrl("/login").
                    .and().build();
    }
//    @Bean
//    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
//
//        return http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                    .anyRequest().authenticated()
//                .and()
//                .oauth2Login().and().build();
//
//    }

}
