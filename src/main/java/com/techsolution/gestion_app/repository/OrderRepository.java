package com.techsolution.gestion_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolution.gestion_app.domain.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Método para listar órdenes por ID de cliente
    List<Order> findByCustomerId(Long customerId);
}
