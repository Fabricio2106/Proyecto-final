package com.techsolution.gestion_app.patterns.proxy;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.patterns.proxy.dto.FinancialReport;

@Service
public class ReportProxy implements Report {
    private final FinancialReportService realService;
    public ReportProxy(FinancialReportService realService) {
        this.realService = realService;
    }
    @Override
    public FinancialReport generate() {
        // verifica si el usuario tiene alguno de los roles permitidos
        boolean hasAccess = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE") 
                            || a.getAuthority().equals("ROLE_CONTADOR"));

        if (!hasAccess) {
            throw new AccessDeniedException("Acceso denegado al reporte financiero");
        }
        // delega al servicio real si tiene permiso
        return realService.generate();
    }
}
