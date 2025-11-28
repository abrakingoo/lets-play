package com.lets_play_e_comerce_platform.lets_play.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/hello", "/home").permitAll() // public endpoints
    //             .anyRequest().authenticated()                  // others require auth
    //         )
    //         .formLogin(form -> form.defaultSuccessUrl("/home")) // default login page
    //         .build();
    // }
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .build();
    }
}