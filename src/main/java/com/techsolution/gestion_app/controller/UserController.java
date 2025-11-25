package com.techsolution.gestion_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.domain.enums.Role;
import com.techsolution.gestion_app.service.UserService;
@RestController
@RequestMapping("/users")
public class UserController {
    // servicio donde está la lógica de usuarios
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // registra un nuevo usuario desde Swagger o cualquier cliente
    @PostMapping
    public User create(
            @RequestParam String username,  // nombre de usuario
            @RequestParam String password,  // contraseña
            @RequestParam Role role         // rol del usuario
    ) {
        // delegamos la creación al servicio
        return userService.create(username, password, role);
    }
    // obtenemos todos los usuarios registrados
    @GetMapping
    public List<User> all() {
        // muestra la lista completa de usuarios
        return userService.getAll();
    }
}
