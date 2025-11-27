package com.techsolution.gestion_app.features.payment.adapter;

import org.springframework.stereotype.Component;
// adaptador para pagos con Plin
@Component
public class PlinAdapter implements PaymentGateway {
    private boolean enabled = true;
    @Override
    public boolean pay(double amount) {
        if (!enabled) {
            System.out.println("Plin está deshabilitado.");
            return false;
        }
        System.out.println("Procesando pago por Plin...");
        return true;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnabled() {
        return enabled;
    }
}
