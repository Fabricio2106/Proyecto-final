package com.techsolution.gestion_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;
import com.techsolution.gestion_app.features.reporting.proxy.ReportProxy;

//endpoints relacionados a reportes financieros.

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportProxy proxy;

    public ReportController(ReportProxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/financial")
    public FinancialReport getFinancialReport() {
        // el controller solo pide el reporte
        // ahora el proxy valida rol antes de delegar al servicio real
        return proxy.generate();
    }
}
