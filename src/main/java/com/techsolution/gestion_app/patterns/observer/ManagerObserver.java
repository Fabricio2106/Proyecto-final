package com.techsolution.gestion_app.patterns.observer;
import com.techsolution.gestion_app.domain.entities.Product;
//observador concreto que representa al GERENTE
public class ManagerObserver implements InventoryObserver {
    @Override
    public void update(Product product) {
        //alerta simple en consola
        System.out.println("GERENTE alerta: El stock de " + product.getProducto() +
                " está bajo. Stock actual: " + product.getStock());
    }
}
