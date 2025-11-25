package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.PaymentConfig;
import com.techsolution.gestion_app.repository.PaymentConfigRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentConfigService {

    private final PaymentConfigRepository repository; // repo para configs de pago

    public PaymentConfig getConfig() {
        // devuelve la config global o crea una nueva si no existe
        return repository.findById(1L).orElseGet(() -> repository.save(new PaymentConfig()));
    }

    public PaymentConfig updateConfig(boolean paypal, boolean yape, boolean plin) {
        PaymentConfig config = getConfig(); // obtenemos la config actual
        config.setPaypalActivo(paypal);     // actualizamos PayPal
        config.setYapeActivo(yape);         // actualizamos Yape
        config.setPlinActivo(plin);         // actualizamos Plin
        return repository.save(config);      // guardamos cambios
    }
}
