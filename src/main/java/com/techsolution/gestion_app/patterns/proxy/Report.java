package com.techsolution.gestion_app.patterns.proxy;

import com.techsolution.gestion_app.patterns.proxy.dto.FinancialReport;

// representa un reporte financiero
 
public interface Report {

    //Genera un reporte
    FinancialReport generate();
}
