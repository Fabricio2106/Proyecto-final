package com.techsolution.gestion_app.patterns.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
public class StandardPrice implements PricingStrategy {
    @Override
    public double calculatePrice(Product product) {
        return product.getPrecio();
    }
}