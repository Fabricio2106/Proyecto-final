package com.techsolution.gestion_app.features.reporting.proxy;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;

// Proxy manual que valida un rol pasado por parámetro
public class FinancialReportProxy implements Report {

    private final FinancialReportService realService;
    private final String userRole; // Rol enviado manualmente por el consumidor

    public FinancialReportProxy(FinancialReportService realService, String userRole) {
        this.realService = realService;
        this.userRole = userRole;
    }

    @Override
    public FinancialReport generate() {

        // Validación manual del rol
        if (!userRole.equalsIgnoreCase("GERENTE") &&
            !userRole.equalsIgnoreCase("CONTADOR")) {

            throw new SecurityException(
                "Acceso denegado: no tiene permisos para ver este reporte"
            );
        }

        // Si tiene permisos, delega al servicio real
        return realService.generate();
    }
}
