package com.techsolution.gestion_app.controller;

import org.springframework.web.bind.annotation.*;

import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.service.OrderService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.List;
// Controlador para manejar todo lo relacionado a pedidos.
// Aquí simplemente recibimos las solicitudes HTTP y las delegamos al servicio.
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    // Servicio donde se encuentra la lógica principal de pedidos.
    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable @NonNull Long orderId) {
        return orderService.getOrderById(orderId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    // Crea un pedido nuevo. El JSON recibido se convierte automáticamente en un objeto Order.
    @PostMapping
    public Order createOrder(@RequestBody @NonNull Order order) {
        return orderService.createOrder(order);
    }

    // ÚNICO endpoint para actualizar el estado de un pedido.
    // Desde aquí se puede cambiar a: PENDIENTE, PROCESANDO, CANCELADO, ENVIADO, ENTREGADO, etc.
    @PutMapping("/{orderId}/estado")
    public Order updateStatus(
            @PathVariable @NonNull Long orderId,
            @RequestParam @NonNull OrderStatus status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // Aplica un descuento a un pedido usando el patrón Strategy + Command.
    @PutMapping("/{orderId}/descuento")
    public Order applyDiscount(
            @PathVariable @NonNull Long orderId,
            @RequestParam double porcentaje
    ) {
        orderService.applyDiscount(orderId, porcentaje);
        return orderService.getOrderById(orderId).orElseThrow();
    }

    // Restaura el último estado guardado de la orden (patrón Memento).
    @PostMapping("/{orderId}/restaurar")
    public void restoreOrderState(@PathVariable @NonNull Long orderId) {
        orderService.getOrderById(orderId)
                .ifPresent(orderService::restoreLastState);
    }

    // Endpoint de prueba para lanzar una excepción de "orden no encontrada".
    @PostMapping("/test/not-found")
    public void testOrderNotFound() {
        throw new com.techsolution.gestion_app.common.exception.OrderNotFoundException(
                "Orden de prueba no encontrada"
        );
    }

    // Endpoint de prueba para lanzar un error genérico y verificar el manejador global de excepciones.
    @PostMapping("/test/generic-error")
    public void testGenericError() {
        throw new RuntimeException("Error inesperado de prueba en pedido");
    }
}
