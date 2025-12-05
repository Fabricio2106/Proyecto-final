package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolution.gestion_app.domain.entities.FinancialReportEntity;

public interface FinancialReportEntityRepository extends JpaRepository<FinancialReportEntity, Long> {
    // findAll(), findById(), save() ya vienen por defecto
}
