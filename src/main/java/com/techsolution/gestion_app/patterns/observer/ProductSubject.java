package com.techsolution.gestion_app.patterns.observer;
import java.util.ArrayList;
import java.util.List;

import com.techsolution.gestion_app.domain.entities.Product;
//implementación concreta de Subject para un producto
public class ProductSubject implements Subject {
    private final Product product; // el producto que estamos observando
    private final List<InventoryObserver> observers = new ArrayList<>(); // lista de observadores
    public ProductSubject(Product product) {
        this.product = product;
    }
    @Override
    public void attach(InventoryObserver observer) {
        observers.add(observer); // agregamos un observador
    }
    @Override
    public void detach(InventoryObserver observer) {
        observers.remove(observer); // quitamos un observador
    }
    @Override
    public void notifyObservers() {
        //solo avisamos si el stock está por debajo del mínimo
        if (product.getStock() < product.getStockMinimo()) {
            for (InventoryObserver obs : observers) {
                obs.update(product); // notificamos a cada observador
            }
        }
    }
    //método práctico: actualizar stock y notificar automáticamente
    public void setStock(int stock) {
        product.setStock(stock);
        notifyObservers();
    }
    public Product getProduct() {
        return product;
    }
}
