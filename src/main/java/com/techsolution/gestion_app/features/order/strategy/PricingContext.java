package com.techsolution.gestion_app.features.order.strategy;

import org.springframework.stereotype.Component;

import com.techsolution.gestion_app.domain.entities.Product;
// contexto para aplicar diferentes estrategias de precios (Strategy Pattern)
@Component
public class PricingContext {

    // estrategia actual, por defecto usamos precio estándar
    private PricingStrategy strategy = new StandardPrice();

    // permite cambiar la estrategia de cálculo de precio en tiempo de ejecución
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    // calcula el precio del producto usando la estrategia actual
    public double calculate(Product product) {
        return strategy.calculatePrice(product);
    }
}
