package com.techsolution.gestion_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.patterns.observer.InventoryObserver;
import com.techsolution.gestion_app.patterns.observer.ManagerObserver;
import com.techsolution.gestion_app.patterns.observer.PurchaseObserver;

// servicio para controlar el stock y avisar a GERENTE y COMPRAS cuando baja
@Service
public class StockObserverService {

    // revisa el stock y dispara alertas si es necesario
    public void checkStock(Product product) {
        // Creamos la lista de observadores aquí mismo
        List<InventoryObserver> observers = new ArrayList<>();
        observers.add(new ManagerObserver());
        observers.add(new PurchaseObserver());

        // si el stock está bajo, avisamos a todos los observadores
        if (product.getStock() < product.getStockMinimo()) {
            for (InventoryObserver obs : observers) {
                obs.update(product);
            }
        }
    }

    // método útil para actualizar el stock y disparar alertas automáticamente
    public void updateStock(Product product, int nuevoStock) {
        product.setStock(nuevoStock); // actualizamos el stock
        checkStock(product);          // revisamos y notificamos
    }
}
