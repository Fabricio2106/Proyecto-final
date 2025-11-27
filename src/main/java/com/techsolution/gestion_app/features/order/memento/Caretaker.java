package com.techsolution.gestion_app.features.order.memento;

import java.util.Stack;

// Administra el historial de estados de una Orden
public class Caretaker {

    private final Stack<OrderMemento> history = new Stack<>();

    public void addMemento(OrderMemento memento) {
        history.push(memento);
    }

    public OrderMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
}
