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

// Proxy que controla el acceso a los reportes financieros según rol de usuario
class FinancialReportProxy implements Report {

    private final FinancialReportService realService;
    private final String userRole; // rol del usuario que solicita el reporte

    public FinancialReportProxy(FinancialReportService realService, String userRole) {
        this.realService = realService;
        this.userRole = userRole;
    }

    @Override
    public FinancialReport generate() {
        // Validamos rol antes de permitir acceso
        if (!userRole.equalsIgnoreCase("GERENTE") && !userRole.equalsIgnoreCase("CONTADOR")) {
            throw new SecurityException("Acceso denegado: no tiene permisos para ver este reporte");
        }
        // si pasa la validación, delegamos al servicio real
        return realService.generate();
    }
}
