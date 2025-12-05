package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techsolution.gestion_app.domain.entities.Payment;
import com.techsolution.gestion_app.domain.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Métodos CRUD ya vienen por defecto (findAll, findById, save, delete, etc.)

    // Total ingresos según el estado del pago
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = :status")
    double getTotalIngresos(@Param("status") PaymentStatus status);

    // Total gastos → por ahora 0, luego puedes reemplazarlo con lógica real
    @Query("SELECT 0")
    double getTotalGastos();

    // Total órdenes
    @Query("SELECT COUNT(o) FROM Order o")
    long countTotalOrders();
}
