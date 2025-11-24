package com.techsolution.gestion_app.controller.dto;
//va arepresentar la información necesaria para procesar un pago el DTO se envía desde el cliente e indica el monto y el método de pago seleccionado.
public class PaymentRequest {
    private double monto;      // cantidad a pagar
    private String metodo;     // paypal, yape, plin

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getMetodo() {
        return metodo;
    }
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
