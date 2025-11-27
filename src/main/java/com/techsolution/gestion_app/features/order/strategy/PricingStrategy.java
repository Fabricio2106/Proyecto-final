package com.techsolution.gestion_app.features.order.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
// interfaz que define cómo calcular el precio de un producto
// parte del Strategy Pattern
public interface PricingStrategy {
    // calcula el precio de un producto según la estrategia
    double calculatePrice(Product product);
}
