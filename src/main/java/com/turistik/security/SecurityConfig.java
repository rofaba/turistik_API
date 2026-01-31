package com.turistik.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para cumplir con el requisito de
 * proteger las operaciones de escritura.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar para facilitar pruebas iniciales
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso público a Swagger y documentación
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Los GET de la API son públicos [cite: 10, 33]
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                        // Cualquier POST/PUT/DELETE requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Usa autenticación básica para el testeo

        return http.build();
    }
}