package com.techsolution.gestion_app.repository;
import com.techsolution.gestion_app.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}