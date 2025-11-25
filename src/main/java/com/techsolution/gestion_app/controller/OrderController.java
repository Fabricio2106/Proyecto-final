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
// Controlador para manejar todo lo relacionado a pedidos.
// Aquí recibimos los datos y se los pasamos al servicio.
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    // servicio donde está la lógica principal de pedidos.
    private final OrderService orderService;
    // crea un pedido nuevo. El JSON se convierte en un objeto Order automáticamente.
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // aquí simplemente delegamos al servicio.
        return orderService.createOrder(order);
    }
    // cambia el estado de un pedido usando su ID y el nuevo estado.
    @PutMapping("/{orderId}/estado")
    public Order updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        // solo llamamos al servicio para actualizarlo.
        return orderService.updateOrderStatus(orderId, status);
    }
    // endpoint de prueba para lanzar una excepción de "orden no encontrada".
    @PostMapping("/test/not-found")
    public void testOrderNotFound() {
        throw new com.techsolution.gestion_app.common.exception.OrderNotFoundException(
                "Orden de prueba no encontrada"
        );
    }
    // endpoint de prueba para lanzar un error genérico.
    @PostMapping("/test/generic-error")
    public void testGenericError() {
        throw new RuntimeException("Error inesperado de prueba en pedido");
    }
}
