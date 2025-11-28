package com.techsolution.gestion_app.features.payment.adapter;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Payment;
import com.techsolution.gestion_app.domain.enums.PaymentStatus;
import com.techsolution.gestion_app.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

// Servicio que maneja los pagos y pasarelas.
@Service
@RequiredArgsConstructor
public class PaymentService {

    // Pasarela de pago actual
    private PaymentGateway gateway;

    // Repositorio para persistir pagos
    private final PaymentRepository paymentRepository;

    // Configura la pasarela a usar
    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    // Ejecuta el pago
    public boolean processPayment(double amount) {
        if (gateway == null) {
            throw new IllegalStateException("No se ha configurado la pasarela de pago.");
        }
        return gateway.pay(amount);
    }

    // Actualiza el estado de un pago
    public void updatePaymentStatus(Long paymentId, PaymentStatus estado) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado: " + paymentId));
        payment.setStatus(estado);
        paymentRepository.save(payment);
    }

    // Guarda un pago nuevo o existente
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
