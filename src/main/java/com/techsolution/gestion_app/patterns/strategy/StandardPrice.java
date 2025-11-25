package com.techsolution.gestion_app.patterns.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
// Estrategia de precio estándar (sin descuento)
// Parte del Strategy Pattern
public class StandardPrice implements PricingStrategy {
    // Solo devuelve el precio original del producto
    @Override
    public double calculatePrice(Product product) {
        return product.getPrecio();
    }
}
