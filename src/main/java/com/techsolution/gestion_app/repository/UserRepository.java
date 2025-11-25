package com.techsolution.gestion_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolution.gestion_app.domain.entities.User;
public interface UserRepository extends JpaRepository<User, Long> {
    // buscar usuario por username
    User findByUsername(String username);
}