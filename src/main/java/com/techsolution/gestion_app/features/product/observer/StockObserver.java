package com.techsolution.gestion_app.features.product.observer;

import org.springframework.stereotype.Component;

import com.techsolution.gestion_app.domain.entities.Product;

// helper del patrón Observer para manejar stock
@Component
public class StockObserver {

    private final ProductSubject subject; // bean global de Spring

    // inyectamos el ProductSubject global de Spring
    public StockObserver(ProductSubject subject) {
        this.subject = subject;
        // Agregamos observadores según roles
        this.subject.attach(new ManagerObserver());
        this.subject.attach(new PurchaseObserver());
    }

    // método para actualizar stock y notificar a los observadores
    public void updateStock(Product product, int nuevoStock) {
        product.setStock(nuevoStock);
        subject.notifyObservers(product); // notifica al product inyectado
    }

    // método para notificar sin cambiar el stock
    public void checkStock(Product product) {
        subject.notifyObservers(product); // revisa stock y alerta si es necesario
    }
}
