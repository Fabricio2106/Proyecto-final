package com.techsolution.gestion_app.features.order.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
public class DynamicPrice implements PricingStrategy {
    @Override
    public double calculatePrice(Product product) {
        int stock = product.getStock();
        if (stock < 15) {
            return product.getPrecio() * 1.10; //en este caso aumenta el precio en un 10% si el stock es bajo
        }
        if (stock > 50) {
            return product.getPrecio() * 0.95; // Reduce el precio en un 5% si el stock es alto
        }
        return product.getPrecio();
    }
}