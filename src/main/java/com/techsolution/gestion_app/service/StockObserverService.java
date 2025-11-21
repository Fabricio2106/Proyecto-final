package com.techsolution.gestion_app.service;
import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
@Service
public class StockObserverService {
    public void checkStock(Product product) {
    if (product.getStock() <= 3) {
        System.out.println("Stock bajo:" + product.getProducto() +" → " + product.getStock()); 
    }

    }
}
 