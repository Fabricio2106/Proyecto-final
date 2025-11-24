package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;
import com.techsolution.gestion_app.domain.entities.PaymentConfig;
import com.techsolution.gestion_app.repository.PaymentConfigRepository;
import lombok.RequiredArgsConstructor;

/**
 * Este servicio se encarga básicamente de manejar la
 * configuración de las pasarelas de pago.
 *
 * La idea es leer la config actual y permitir actualizarla
 * cuando el administrador cambie algo desde el panel.
 */
@Service
@RequiredArgsConstructor
public class PaymentConfigService {

    private final PaymentConfigRepository repository;

    /**
     * Devuelvo siempre la configuración global.
     * En este proyecto solo existe un registro (id = 1).
     */
    public PaymentConfig getConfig() {
        return repository.findById(1L).orElseGet(() -> {
            // Si no existe (primera ejecución), creo una config por defecto
            PaymentConfig config = new PaymentConfig();
            return repository.save(config);
        });
    }

    /**
     * Actualiza la configuración según lo que envíe el admin.
     */
    public PaymentConfig updateConfig(boolean paypal, boolean yape, boolean plin) {
        PaymentConfig config = getConfig();
        config.setPaypalActivo(paypal);
        config.setYapeActivo(yape);
        config.setPlinActivo(plin);
        return repository.save(config);
    }
}
