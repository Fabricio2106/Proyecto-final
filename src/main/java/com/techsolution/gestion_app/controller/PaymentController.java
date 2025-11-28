package com.techsolution.gestion_app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.controller.dto.PaymentRequest;
import com.techsolution.gestion_app.domain.entities.PaymentConfig;
import com.techsolution.gestion_app.features.payment.adapter.PayPalAdapter;
import com.techsolution.gestion_app.features.payment.adapter.PaymentService;
import com.techsolution.gestion_app.features.payment.adapter.PlinAdapter;
import com.techsolution.gestion_app.features.payment.adapter.YapeAdapter;
import com.techsolution.gestion_app.service.PaymentConfigService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PayPalAdapter payPalAdapter;
    private final YapeAdapter yapeAdapter;
    private final PlinAdapter plinAdapter;
    private final PaymentConfigService paymentConfigService;

    // procesar un pago según el método enviado
    @PostMapping("/order/{orderId}")
    public String processPayment(@PathVariable Long orderId, @RequestBody PaymentRequest paymentRequest) {
        double monto = paymentRequest.getMonto();
        String metodo = paymentRequest.getMetodo().toLowerCase();
        PaymentConfig config = paymentConfigService.getConfig();

        // verificamos si la pasarela está habilitada
        if ((metodo.equals("paypal") && !config.isPaypalActivo()) ||
            (metodo.equals("yape") && !config.isYapeActivo()) ||
            (metodo.equals("plin") && !config.isPlinActivo())) {
            return "La pasarela " + metodo + " está deshabilitada.";
        }

        // elegimos el adaptador usando rule switch
        var gateway = switch (metodo) {
            case "paypal" -> payPalAdapter;
            case "yape" -> yapeAdapter;
            case "plin" -> plinAdapter;
            default -> null;
        };
        if (gateway == null) {
            return "Método no válido. Usa: paypal, yape o plin.";
        }

        // configuramos la pasarela en el servicio y procesamos el pago con manejo de errores
        paymentService.setGateway(gateway);
        try {
            boolean ok = paymentService.processPayment(monto);
            return ok ? "Pago realizado con éxito usando " + metodo : "No se pudo procesar el pago.";
        } catch (Exception e) {
            return "Error procesando pago: " + e.getMessage();
        }
    }

    // endpoint para probar errores manualmente
    @PostMapping("/test/error")
    public void testPaymentError() {
        throw new RuntimeException("Error inesperado en prueba de pago");
    }

    // control de pasarelas (realmente modifica la configuración)
    @PostMapping("/config/{gateway}/enable")
    public String enableGateway(@PathVariable String gateway) {
        toggleGateway(gateway, true); // habilita en la config real
        return "La pasarela " + gateway + " ahora está habilitada";
    }

    @PostMapping("/config/{gateway}/disable")
    public String disableGateway(@PathVariable String gateway) {
        toggleGateway(gateway, false); // deshabilita en la config real
        return "La pasarela " + gateway + " ahora está deshabilitada";
    }

    // método privado que actualiza la configuración real usando updateConfig
    private void toggleGateway(String gateway, boolean enabled) {
        PaymentConfig config = paymentConfigService.getConfig(); // obtenemos la config actual
        gateway = gateway.toLowerCase();
        switch (gateway) {
            case "paypal" -> config.setPaypalActivo(enabled);
            case "yape" -> config.setYapeActivo(enabled);
            case "plin" -> config.setPlinActivo(enabled);
            default -> throw new IllegalArgumentException("Pasarela no válida: " + gateway);
        }
        // guardamos cambios usando updateConfig existente
        paymentConfigService.updateConfig(config.isPaypalActivo(), config.isYapeActivo(), config.isPlinActivo());
    }

}
