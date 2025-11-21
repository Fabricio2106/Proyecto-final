package com.techsolution.gestion_app.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //esto es para evitar problemas al hacer POST desde pruebas
                .csrf(csrf -> csrf.disable())

                // esto otorga el permiso total a la API y a la consola H2
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**", "/h2-console/**").permitAll()
                        .anyRequest().permitAll()
                )

                // esto es necesario para que la consola de H2 funcione
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // desactiva el login por defecto
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
