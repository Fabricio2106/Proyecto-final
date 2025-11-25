package com.techsolution.gestion_app.patterns.adapter;

import org.springframework.stereotype.Component;
// adaptador para pagos con Yape
@Component
public class YapeAdapter implements PaymentGateway {
    private boolean enabled = true;
    @Override
    public boolean pay(double amount) {
        if (!enabled) {
            System.out.println("Yape está deshabilitado.");
            return false;
        }
        System.out.println("Procesando pago por Yape...");
        return true;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnabled() {
        return enabled;
    }
}
