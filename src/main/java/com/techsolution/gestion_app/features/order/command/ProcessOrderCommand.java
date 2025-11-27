package com.techsolution.gestion_app.features.order.command;

import com.techsolution.gestion_app.service.OrderService;

public class ProcessOrderCommand implements Command {

    private final OrderService orderService;
    private final Long orderId;

    public ProcessOrderCommand(OrderService orderService, Long orderId) {
        this.orderService = orderService;
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        orderService.processOrder(orderId);
    }
}
