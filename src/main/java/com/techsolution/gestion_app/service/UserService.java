package com.techsolution.gestion_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.User;
import com.techsolution.gestion_app.domain.enums.Role;
import com.techsolution.gestion_app.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // registrar usuario nuevo
    public User create(String username, String password, Role role) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        return userRepository.save(u);
    }

    // lista todos
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
