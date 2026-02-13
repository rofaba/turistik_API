package com.turistik.security;

import jakarta.servlet.http.HttpServletResponse;
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
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF para pruebas en Swagger
                .authorizeHttpRequests(auth -> auth
                        // 1. Acceso público para consultas
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        // 2. Acceso a la documentación técnica
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 3. Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                // ESTO ES LO QUE ELIMINA EL POP-UP DEL NAVEGADOR
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"error\": \"No autorizado\", \"message\": \"Credenciales de administrador requeridas para esta operacion.\" }");
                        })
                )
                .httpBasic(Customizer.withDefaults()); // Mantenemos la lógica de login básico

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