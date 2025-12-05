package com.techsolution.gestion_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.features.reporting.dto.ReportRequest;
import com.techsolution.gestion_app.features.reporting.dto.ReportResponse;
import com.techsolution.gestion_app.features.reporting.proxy.FinancialReportProxy;
import com.techsolution.gestion_app.features.reporting.proxy.FinancialReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final FinancialReportService realService;

    public ReportController(FinancialReportService realService) {
        this.realService = realService;
    }

    /**
     * Genera un reporte financiero validando el rol mediante un Proxy manual.
     */
    @PostMapping("/financial")
    public ResponseEntity<?> generateFinancialReport(
            @RequestBody(required = false) ReportRequest request,
            @RequestHeader("Role") String role) {

        try {
            // Crear el Proxy manual y pasar el rol del encabezado
            FinancialReportProxy proxy = new FinancialReportProxy(realService, role);

            // Generar el reporte financiero
            var report = proxy.generate();

            // Crear respuesta estándar
            ReportResponse response = new ReportResponse(
                    "Reporte Financiero",
                    java.time.LocalDateTime.now().toString(),
                    report
            );

            return ResponseEntity.ok(response);

        } catch (SecurityException ex) {

            // Cuando el rol NO tiene permisos
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ex.getMessage());

        } catch (Exception ex) {

            // Cualquier error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generando el reporte financiero: " + ex.getMessage());
        }
    }
}
