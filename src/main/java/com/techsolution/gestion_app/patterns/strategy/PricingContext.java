package com.techsolution.gestion_app.patterns.strategy;
import org.springframework.stereotype.Component;

import com.techsolution.gestion_app.domain.entities.Product;
@Component
public class PricingContext {
    private PricingStrategy strategy = new StandardPrice();
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }
    public double calculate(Product product) {
        return strategy.calculatePrice(product);
    }

}
