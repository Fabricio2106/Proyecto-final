package com.techsolution.gestion_app.features.order.command;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.service.OrderService;


public class CreateOrderCommand implements Command {

    private final OrderService orderService;
    private final Order order;

    public CreateOrderCommand(OrderService orderService, Order order) {
        this.orderService = orderService;
        this.order = order;
    }

    @Override
    public void execute() {
        orderService.createOrder(order);
    }
}
