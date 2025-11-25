package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolution.gestion_app.domain.entities.Payment;

// Repositorio para manejar operaciones de Payment en la base de datos
// Extiende JpaRepository para obtener métodos CRUD automáticamente
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Puedes agregar consultas personalizadas aquí si lo necesitas en el futuro

}