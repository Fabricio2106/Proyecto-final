package com.techsolution.gestion_app.features.product.observer;
import com.techsolution.gestion_app.domain.entities.Product;
// helper del patrón Observer para manejar stock
public class StockObserver {
    private final ProductSubject subject; // subject concreto para el producto
    // constructor: recibe el producto a observar
    public StockObserver(Product product) {
        this.subject = new ProductSubject(product);
        // Agregamos observadores según roles
        this.subject.attach(new ManagerObserver());
        this.subject.attach(new PurchaseObserver());
    }
    // método para actualizar stock y notificar a los observadores
    public void updateStock(int nuevoStock) {
        subject.setStock(nuevoStock); // actualiza stock y notifica automáticamente
    }
    // método para notificar sin cambiar el stock
    public void checkStock() {
        subject.notifyObservers(); // revisa stock y alerta si es necesario
    }
    // obtener el producto que se está observando
    public Product getProduct() {
        return subject.getProduct();
    }
}
