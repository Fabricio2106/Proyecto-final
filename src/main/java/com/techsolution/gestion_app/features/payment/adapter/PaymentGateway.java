package com.techsolution.gestion_app.features.payment.adapter;
// forma general de comunicarse con una pasarela de pago.
// así puedo cambiar de PayPal a Yape sin tocar el resto del sistema.
public interface PaymentGateway {
    boolean pay(double amount);
}
