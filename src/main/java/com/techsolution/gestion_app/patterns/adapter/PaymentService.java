package com.techsolution.gestion_app.patterns.adapter;
import org.springframework.stereotype.Service;
// servicio que maneja los pagos.
// aquí solo indicamos qué pasarela se usará y ejecutamos el pago.
@Service
public class PaymentService {
    // pasarela que se aplicará según el método elegido.
    private PaymentGateway gateway;
    // se usa cuando el controlador decide qué pasarela aplicar.
    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }
    // ejecuta el pago. Devuelve true si salió bien.
    public boolean processPayment(double amount) {
        if (gateway == null) {
            throw new IllegalStateException("No se ha configurado la pasarela de pago.");
        }
        // aquí se llama al método pay() de cada adaptador.
        return gateway.pay(amount);
    }
}
