package com.techsolution.gestion_app.features.order.memento;

import com.techsolution.gestion_app.domain.entities.Order;

// Maneja la creación y restauración de estados de una orden
public class OrderOriginator {

    private Order order; // se mantiene así, NO debe ser final

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderMemento saveState() {
        return new OrderMemento(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderDate()
        );
    }

    public void restoreState(OrderMemento memento) {
        order.setStatus(memento.getStatus());
        order.setTotalAmount(memento.getTotalAmount());
        order.setOrderDate(memento.getLastUpdate());
    }
}
