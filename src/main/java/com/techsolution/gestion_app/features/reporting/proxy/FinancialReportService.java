package com.techsolution.gestion_app.features.reporting.proxy;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;

// servicio real que crea el reporte financiero completo.

@Service
public class FinancialReportService implements Report {

    @Override
    public FinancialReport generate() {
        // Lógica real de reporte
        return new FinancialReport("Reporte financiero completo del sistema");
    }
}
