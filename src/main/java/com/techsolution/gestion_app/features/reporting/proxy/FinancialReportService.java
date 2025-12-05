package com.techsolution.gestion_app.features.reporting.proxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.FinancialReportEntity;
import com.techsolution.gestion_app.domain.enums.PaymentStatus;
import com.techsolution.gestion_app.features.reporting.dto.FinancialReport;
import com.techsolution.gestion_app.repository.FinancialReportEntityRepository;
import com.techsolution.gestion_app.repository.PaymentRepository;

@Service
public class FinancialReportService {

    private final FinancialReportEntityRepository reportRepo; // repositorio de reportes
    private final PaymentRepository paymentRepo;               // repositorio de pagos

    public FinancialReportService(FinancialReportEntityRepository reportRepo,
                                  PaymentRepository paymentRepo) {
        this.reportRepo = reportRepo;
        this.paymentRepo = paymentRepo;
    }

    //Genera un reporte financiero, lo guarda en la base de datos y retorna un DTO.
    public FinancialReport generate() {
        // Calcular datos
        double ingresos = paymentRepo.getTotalIngresos(PaymentStatus.PAGADO);
        double gastos = paymentRepo.getTotalGastos();
        long totalOrders = paymentRepo.countTotalOrders();

        double utilidad = ingresos - gastos;
        String resumen = "Reporte financiero generado automáticamente desde la base de datos.";

        // Guardar en entidad JPA
        FinancialReportEntity entity = new FinancialReportEntity();
        entity.setTotalIngresos(ingresos);
        entity.setTotalGastos(gastos);
        entity.setTotalOrders(totalOrders);
        entity.setGeneratedAt(LocalDateTime.now());
        reportRepo.save(entity);

        // Retornar DTO para uso en el frontend
        return new FinancialReport(ingresos, gastos, utilidad, totalOrders, resumen);
    }

    //Devuelve todos los reportes guardados.
     
    public List<FinancialReportEntity> getAllReports() {
        return reportRepo.findAll();
    }

    //Devuelve un reporte por su ID, si existe.
     
    public Optional<FinancialReportEntity> getReportById(Long id) {
        return reportRepo.findById(id);
    }
}
