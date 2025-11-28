package com.techsolution.gestion_app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.NonNull;   //  evita warnings
import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.common.exception.InsufficientStockException;
import com.techsolution.gestion_app.common.exception.OrderNotFoundException;
import com.techsolution.gestion_app.common.exception.ProductNotFoundException;
import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.entities.OrderItem;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.features.order.memento.Caretaker;
import com.techsolution.gestion_app.features.order.memento.OrderMemento;
import com.techsolution.gestion_app.features.order.memento.OrderOriginator;
import com.techsolution.gestion_app.features.order.strategy.PricingContext;
import com.techsolution.gestion_app.features.order.strategy.StandardPrice;
import com.techsolution.gestion_app.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final StockObserverService stockObserverService; // ← observer para stock
    private final PricingContext pricingContext;

    // Memento
    private final OrderOriginator originator = new OrderOriginator();
    private final Caretaker caretaker = new Caretaker();

    // trae una orden por id
    public Optional<Order> getOrderById(@NonNull Long id) {   // ← evita warning
        return orderRepository.findById(id);
    }

    // crear nueva orden
    @Transactional
    public Order createOrder(@NonNull Order order) {   // ← evita warning

        // Guardar estado inicial
        originator.setOrder(order);
        caretaker.addMemento(originator.saveState());

        order.setStatus(OrderStatus.PENDIENTE);
        order.setOrderDate(LocalDateTime.now());
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());
        order.setCustomer(customer);
        pricingContext.setStrategy(new StandardPrice());
        double total = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            Product product = productService.getProductById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Producto no existe: " + item.getProduct().getId()));
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto: " + product.getProducto());
            }
            double priceForProduct = pricingContext.calculate(product);
            total += priceForProduct * item.getQuantity();
            item.setUnitPrice(priceForProduct);
            item.setProduct(product);
            item.setOrder(order);
            // Observer: verificar stock y notificar si baja del mínimo
            stockObserverService.checkStock(product);
        }
        order.setTotalAmount(total);
        descontarStock(order);
        if (order.getPayment() != null) {
            order.getPayment().setOrder(order);
        }

        // Guardar estado final de la creación
        originator.setOrder(order);
        caretaker.addMemento(originator.saveState());

        return orderRepository.save(order);
    }

    // Restaurar último estado de la orden (Memento)
    public void restoreLastState(@NonNull Order order) {
        OrderMemento memento = caretaker.undo();
        if (memento != null) {
            originator.setOrder(order);
            originator.restoreState(memento);
            orderRepository.save(order);
        }
    }

    // actualizar estado de la orden
    @Transactional
    public Order updateOrderStatus(@NonNull Long orderId, @NonNull OrderStatus status) { // ← evita warning
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + orderId));
        if (status == OrderStatus.ENTREGADO && !order.isStockDescontado()) {
            descontarStock(order);
        }
        if (status == OrderStatus.CANCELADO && order.isStockDescontado()) {
            restaurarStock(order);
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // descontar stock
    private void descontarStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
              Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto: " + product.getProducto());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productService.saveProduct(product);

            // Observer: notificar cambios de stock
            stockObserverService.checkStock(product);
        }
        order.setStockDescontado(true);
    }

    // restaurar stock
    private void restaurarStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productService.saveProduct(product);
            // Observer: notificar cambios de stock
            stockObserverService.checkStock(product);
        }
        order.setStockDescontado(false);
    }

    // aplicar descuento
    public void applyDiscount(@NonNull Long orderId, double percentage) { // ← evita warning
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + orderId));
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Porcentaje de descuento inválido");
        }
        double discountAmount = order.getTotalAmount() * (percentage / 100);
        order.setTotalAmount(order.getTotalAmount() - discountAmount);
        orderRepository.save(order);
    }

    // cancelar orden
    @Transactional
    public Order cancelOrder(@NonNull Long orderId) {  // ← evita warning
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + orderId));
        if (order.isStockDescontado()) {
            restaurarStock(order);
        }
        order.setStatus(OrderStatus.CANCELADO);
        return orderRepository.save(order);
    }

    // procesar orden
    @Transactional
    public Order processOrder(@NonNull Long orderId) {  // ← evita warning
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + orderId));
        order.setStatus(OrderStatus.PROCESANDO);
        return orderRepository.save(order);
    }
}
