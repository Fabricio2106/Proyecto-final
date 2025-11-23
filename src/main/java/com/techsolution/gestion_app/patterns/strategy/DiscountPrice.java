package com.techsolution.gestion_app.patterns.strategy;
import com.techsolution.gestion_app.domain.entities.Product;
public class DiscountPrice implements PricingStrategy {
   private final double discount;
   public DiscountPrice(double discount) {
       this.discount = discount;
   }
    @Override
    public double calculatePrice(Product product) {
        return product.getPrecio() * (1 - discount);
    }
    
}
