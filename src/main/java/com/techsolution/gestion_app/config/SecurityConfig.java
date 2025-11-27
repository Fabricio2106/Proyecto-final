package com.techsolution.gestion_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//configuración principal de seguridad.
//define accesos por rol y usuarios en memoria para pruebas.
@Configuration
public class SecurityConfig {

    // configuramos qué rutas requieren login y quién puede entrar
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // quitamos CSRF solo para pruebas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/**").permitAll() // registro y login libre
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // documentación libre
                .requestMatchers("/reports/**").hasAnyRole("GERENTE","CONTADOR") // solo GERENTE y CONTADOR pueden ver reportes
                .anyRequest().authenticated() // lo demás necesita login
            )
            .httpBasic();

        return http.build();
    }

    // Creamos usuarios de prueba en memoria 
    //incluye gerente,contador y clietne
    @Bean
    public UserDetailsService users() {
        var userManager = new InMemoryUserDetailsManager();
        // usuario GERENTE
        userManager.createUser(
            User.withUsername("GERENTE") // usamos el rol como nombre
                .password("{noop}123")    // contraseña simple para pruebas
                .roles("GERENTE")         // asignamos el rol
                .build()
        );
        // usuario CONTADOR
        userManager.createUser(
            User.withUsername("CONTADOR")
                .password("{noop}123")
                .roles("CONTADOR")
                .build()
        );
        // usuario CLIENTE (no puede ver reportes)
        userManager.createUser(
            User.withUsername("CLIENTE")
                .password("{noop}123")
                .roles("CLIENTE")
                .build()
        );
        return userManager;
    }
}
