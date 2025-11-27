package com.techsolution.gestion_app.features.order.command;

import com.techsolution.gestion_app.service.OrderService;

public class ApplyDiscountCommand implements Command {

    private final OrderService orderService;
    private final Long orderId;
    private final double discountPercentage;

    public ApplyDiscountCommand(OrderService orderService, Long orderId, double discountPercentage) {
        this.orderService = orderService;
        this.orderId = orderId;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void execute() {
        orderService.applyDiscount(orderId, discountPercentage);
    }
}
