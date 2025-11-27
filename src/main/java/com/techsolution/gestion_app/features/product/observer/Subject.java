package com.techsolution.gestion_app.features.product.observer;
public interface Subject {
    void attach(InventoryObserver observer);  // agregar observador
    void detach(InventoryObserver observer);  // quitar observador
    void notifyObservers();                   // avisar a todos los observadores
}
