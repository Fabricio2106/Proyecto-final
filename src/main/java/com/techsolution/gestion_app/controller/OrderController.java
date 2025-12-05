package com.techsolution.gestion_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    // ENDPOINTS PARA CONSULTAS (GET)


    // Listar todos los pedidos
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Obtener un pedido por su ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable @NonNull Long orderId) {
        return orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    // ENDPOINTS PARA CREAR Y MODIFICAR PEDIDOS


    // Crear un nuevo pedido
    @PostMapping
    public Order createOrder(@RequestBody @NonNull Order order) {
        return orderService.createOrder(order);
    }

    // Actualizar el estado de un pedido
    @PutMapping("/{orderId}/estado")
    public Order updateStatus(
            @PathVariable @NonNull Long orderId,
            @RequestParam @NonNull OrderStatus status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // Aplicar un descuento a un pedido
    @PutMapping("/{orderId}/descuento")
    public Order applyDiscount(
            @PathVariable @NonNull Long orderId,
            @RequestParam double porcentaje
    ) {
        orderService.applyDiscount(orderId, porcentaje);
        return orderService.getOrderById(orderId).orElseThrow();
    }

    // Restaurar el pedido a su último estado guardado
    @PostMapping("/{orderId}/restaurar")
    public void restoreOrderState(@PathVariable @NonNull Long orderId) {
        orderService.getOrderById(orderId)
                .ifPresent(orderService::restoreLastState);
    }

    // ENDPOINTS DE PRUEBA
  

    // Generar error de "pedido no encontrado" para pruebas
    @PostMapping("/test/not-found")
    public void testOrderNotFound() {
        throw new com.techsolution.gestion_app.common.exception.OrderNotFoundException(
                "Orden de prueba no encontrada"
        );
    }

    // Generar error genérico para pruebas
    @PostMapping("/test/generic-error")
    public void testGenericError() {
        throw new RuntimeException("Error inesperado de prueba en pedido");
    }
}
