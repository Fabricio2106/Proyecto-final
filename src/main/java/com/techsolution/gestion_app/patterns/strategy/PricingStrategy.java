package com.techsolution.gestion_app.patterns.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
public interface PricingStrategy {
    double calculatePrice(Product product);
}