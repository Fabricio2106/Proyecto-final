package com.techsolution.gestion_app.domain.entities;
import com.techsolution.gestion_app.domain.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // identificador del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // nombre único para iniciar sesión
    @Column(unique = true, nullable = false)
    private String username;
    // contraseña encriptada
    @Column(nullable = false)
    private String password;
    // rol del usuario dentro del sistema
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
