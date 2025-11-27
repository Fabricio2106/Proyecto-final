package com.techsolution.gestion_app.features.product.observer;

import com.techsolution.gestion_app.domain.entities.Product;
public interface InventoryObserver {
    // se llama cuando baja el stock de un producto
    void update(Product product);
}
