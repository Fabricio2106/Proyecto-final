 package com.techsolution.gestion_app.features.reporting.proxy;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;

// Representa un reporte
public interface Report {
    FinancialReport generate();
}
