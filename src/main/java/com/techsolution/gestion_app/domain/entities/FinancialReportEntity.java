package com.techsolution.gestion_app.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "financial_reports")
public class FinancialReportEntity {

    // Identificador único del reporte
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Total de ingresos registrados
    private double totalIngresos;

    // Total de gastos registrados
    private double totalGastos;

    // Cantidad total de órdenes
    private long totalOrders;

    // Fecha y hora en que se generó el reporte
    private LocalDateTime generatedAt;
    // GETTERS Y SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getTotalIngresos() { return totalIngresos; }
    public void setTotalIngresos(double totalIngresos) { this.totalIngresos = totalIngresos; }

    public double getTotalGastos() { return totalGastos; }
    public void setTotalGastos(double totalGastos) { this.totalGastos = totalGastos; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
