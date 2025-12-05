package com.techsolution.gestion_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolution.gestion_app.domain.entities.Order;
import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}