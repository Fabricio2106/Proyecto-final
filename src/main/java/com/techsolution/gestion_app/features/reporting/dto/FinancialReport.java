package com.techsolution.gestion_app.features.reporting.dto;

public class FinancialReport {

    private double ingresos;
    private double gastos;
    private double utilidad;
    private long totalOrders;
    private String resumen;

    public FinancialReport() {}

    public FinancialReport(double ingresos, double gastos, double utilidad, long totalOrders, String resumen) {
        this.ingresos = ingresos;
        this.gastos = gastos;
        this.utilidad = utilidad;
        this.totalOrders = totalOrders;
        this.resumen = resumen;
    }

    // Getters y setters
    public double getIngresos() { return ingresos; }
    public void setIngresos(double ingresos) { this.ingresos = ingresos; }

    public double getGastos() { return gastos; }
    public void setGastos(double gastos) { this.gastos = gastos; }

    public double getUtilidad() { return utilidad; }
    public void setUtilidad(double utilidad) { this.utilidad = utilidad; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
}
