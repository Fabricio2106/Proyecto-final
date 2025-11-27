package com.techsolution.gestion_app.features.reporting.proxy;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;

// representa un reporte financiero
 
public interface Report {

    //Genera un reporte
    FinancialReport generate();
}
