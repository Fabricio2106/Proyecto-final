package com.techsolution.gestion_app.patterns.proxy.dto;
//objeto que contiene los datos del reporte financiero.
public class FinancialReport {
    private final String info;
    public FinancialReport(String info) {
        this.info = info;
    }
    public String getInfo() {
        return info;
    }
}
