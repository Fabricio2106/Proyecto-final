package com.techsolution.gestion_app.features.order.command;

import com.techsolution.gestion_app.service.OrderService;

public class CancelOrderCommand implements Command {

    private final OrderService orderService;
    private final Long orderId;

    public CancelOrderCommand(OrderService orderService, Long orderId) {
        this.orderService = orderService;
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        orderService.cancelOrder(orderId);
    }
}
