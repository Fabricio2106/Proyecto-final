package com.techsolution.gestion_app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.controller.dto.PaymentRequest;
import com.techsolution.gestion_app.patterns.adapter.PayPalAdapter;
import com.techsolution.gestion_app.patterns.adapter.PaymentService;
import com.techsolution.gestion_app.patterns.adapter.PlinAdapter;
import com.techsolution.gestion_app.patterns.adapter.YapeAdapter;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PayPalAdapter payPalAdapter;
    private final YapeAdapter yapeAdapter;
    private final PlinAdapter plinAdapter;
    // Procesar un pago según el método enviado
    @PostMapping("/order/{orderId}")
    public String processPayment(@PathVariable Long orderId, @RequestBody PaymentRequest paymentRequest) {
        double monto = paymentRequest.getMonto();
        String metodo = paymentRequest.getMetodo().toLowerCase();
        // Elegimos el adaptador usando rule switch
        var gateway = switch (metodo) {
            case "paypal" -> payPalAdapter;
            case "yape" -> yapeAdapter;
            case "plin" -> plinAdapter;
            default -> null;
        };
        if (gateway == null) {
            return "Método no válido. Usa: paypal, yape o plin.";
        }
        // Configuramos la pasarela en el servicio y procesamos el pago
        paymentService.setGateway(gateway);
        boolean ok = paymentService.processPayment(monto);
        return ok ? "Pago realizado con éxito usando " + metodo : "No se pudo procesar el pago.";
    }
    // Endpoint para probar errores manualmente
    @PostMapping("/test/error")
    public void testPaymentError() {
        throw new RuntimeException("Error inesperado en prueba de pago");
    }
    // Control de pasarelas (simulado)
    @PostMapping("/config/{gateway}/enable")
    public String enableGateway(@PathVariable String gateway) {
        return toggleGatewayMessage(gateway, true);
    }
    @PostMapping("/config/{gateway}/disable")
    public String disableGateway(@PathVariable String gateway) {
        return toggleGatewayMessage(gateway, false);
    }
    // Solo devuelve un mensaje de estado. La lógica real de habilitar/deshabilitar
    // debería ir en PaymentConfig y no en los adaptadores
    private String toggleGatewayMessage(String gateway, boolean enabled) {
        gateway = gateway.toLowerCase();
        return switch (gateway) {
            case "paypal", "yape", "plin" ->
                "La pasarela " + gateway + " ahora está " + (enabled ? "habilitada" : "deshabilitada");
            default -> "Pasarela no válida. Usa: paypal, yape o plin.";
        };
    }
}
