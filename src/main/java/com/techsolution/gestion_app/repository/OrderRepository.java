package com.techsolution.gestion_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolution.gestion_app.domain.entities.Order;
public interface OrderRepository extends JpaRepository<Order, Long> {
}