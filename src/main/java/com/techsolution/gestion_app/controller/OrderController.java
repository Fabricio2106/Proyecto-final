package com.techsolution.gestion_app.controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.service.OrderService;

import lombok.RequiredArgsConstructor;
//desde aquí controlamos todo lo que tenga que ver con los pedidos básicamente este archivo recibe las peticiones del cliente y se las pasa al servicio.
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    // Aquí usamos el servicio donde está la lógica de los pedidos.
    private final OrderService orderService;
    // aca se va a crear un pedido nuevo el pedido llega desde el cuerpo de la petición (JSON)y lo enviamos al servicio para guardarlo.
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    // Cambiar el estado de un pedido
    @PutMapping("/{orderId}/estado")
    public Order updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
