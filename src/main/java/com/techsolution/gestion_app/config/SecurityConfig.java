package com.techsolution.gestion_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // deshabilita CSRF para pruebas
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // todo endpoint libre, sin login ni rol
            );
            // No se configura httpBasic() para deshabilitar autenticación

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // Usuarios definidos solo para pruebas futuras si se quiere
        var userManager = new InMemoryUserDetailsManager();

        userManager.createUser(
            User.withUsername("GERENTE")
                .password("{noop}123")
                .roles("GERENTE")
                .build()
        );
        userManager.createUser(
            User.withUsername("CONTADOR")
                .password("{noop}123")
                .roles("CONTADOR")
                .build()
        );
        userManager.createUser(
            User.withUsername("CLIENTE")
                .password("{noop}123")
                .roles("CLIENTE")
                .build()
        );
        return userManager;
    }
}
