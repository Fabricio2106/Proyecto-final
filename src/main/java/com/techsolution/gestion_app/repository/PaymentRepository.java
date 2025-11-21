package com.techsolution.gestion_app.repository;
import  com.techsolution.gestion_app.domain.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}