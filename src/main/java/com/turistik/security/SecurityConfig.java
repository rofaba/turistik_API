package com.turistik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necesario para POST desde Swagger
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitimos que CUALQUIERA vea los datos (GET)
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()

                        // 2. Permitimos acceso a la documentación
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 3. BLOQUEAMOS todo lo demás (POST, PUT, DELETE) tras login
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Login sencillo para el vídeo [cite: 22]

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creamos un usuario administrador para las pruebas del vídeo
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}