package com.techsolution.gestion_app.features.payment.adapter;

import org.springframework.stereotype.Component;

// adaptador para pagos con PayPal
@Component
public class PayPalAdapter implements PaymentGateway {

    // indica si este método de pago está habilitado
    private boolean enabled = true;

    @Override
    public boolean pay(double amount) {
        if (!enabled) {
            System.out.println("PayPal está deshabilitado.");
            return false;
        }

        System.out.println("Procesando pago por PayPal...");
        return true;
    }

    // activa o desactivar la pasarela
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // saber si está activa
    public boolean isEnabled() {
        return enabled;
    }
}
