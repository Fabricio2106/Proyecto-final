package com.techsolution.gestion_app.patterns.adapter;

import org.springframework.stereotype.Component;

// Adaptador para pagos con PayPal
@Component
public class PayPalAdapter implements PaymentGateway {

    // Indica si este método de pago está habilitado
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

    // Activar o desactivar la pasarela
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // Saber si está activa
    public boolean isEnabled() {
        return enabled;
    }
}
