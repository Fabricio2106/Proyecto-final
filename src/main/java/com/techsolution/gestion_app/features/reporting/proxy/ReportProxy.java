package com.techsolution.gestion_app.features.reporting.proxy;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;

// Proxy que valida roles usando Spring Security
@Service
public class ReportProxy implements Report {

    private final FinancialReportService realService;

    public ReportProxy(FinancialReportService realService) {
        this.realService = realService;
    }

    @Override
    public FinancialReport generate() {

        // Verifica si el usuario autenticado tiene uno de los roles permitidos
        boolean hasAccess = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> 
                        a.getAuthority().equals("ROLE_GERENTE") ||
                        a.getAuthority().equals("ROLE_CONTADOR")
                );

        if (!hasAccess) {
            throw new AccessDeniedException("Acceso denegado al reporte financiero");
        }

        // Si tiene permiso, delega la generación al servicio real
        return realService.generate();
    }
}
