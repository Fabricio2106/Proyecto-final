package com.techsolution.gestion_app.features.order.memento;

import java.time.LocalDateTime;

import com.techsolution.gestion_app.domain.enums.OrderStatus;

// Guarda un estado inmutable de la orden para permitir restauración
public class OrderMemento {

    private final Long id;
    private final OrderStatus status;
    private final Double totalAmount;
    private final LocalDateTime lastUpdate;

    public OrderMemento(Long id, OrderStatus status, Double totalAmount, LocalDateTime lastUpdate) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
