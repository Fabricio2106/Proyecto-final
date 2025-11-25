package com.techsolution.gestion_app.patterns.strategy;
import com.techsolution.gestion_app.domain.entities.Product;

// estrategia de precio con descuento (Strategy Pattern)
public class DiscountPrice implements PricingStrategy {
    private final double discount; // porcentaje de descuento (0.1 = 10%)
    //constructor: recibimos el porcentaje de descuento
    public DiscountPrice(double discount) {
        this.discount = discount;
    }
    //calcula el precio final aplicando el descuento
    @Override
    public double calculatePrice(Product product) {
        // precio final = precio original * (1 - descuento)
        return product.getPrecio() * (1 - discount);
    }
}
