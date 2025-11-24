package com.techsolution.gestion_app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.common.exception.InsufficientStockException;
import com.techsolution.gestion_app.common.exception.OrderNotFoundException;
import com.techsolution.gestion_app.common.exception.ProductNotFoundException;
import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.entities.OrderItem;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.patterns.strategy.PricingContext;
import com.techsolution.gestion_app.patterns.strategy.StandardPrice;
import com.techsolution.gestion_app.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final StockObserverService stockObserverService;
    private final PricingContext pricingContext;

    // Trae una orden por id (esto lo usa el controlador para validar antes de pagar)
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Va a crear una nueva orden y calcular su total
    @Transactional
    public Order createOrder(Order order) {

        // Inicializa estado y fecha de la orden
        order.setStatus(OrderStatus.PENDIENTE);
        order.setOrderDate(LocalDateTime.now());

        // Carga la información completa del cliente
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());
        order.setCustomer(customer);

        // Define la estrategia de cálculo de precios
        pricingContext.setStrategy(new StandardPrice());
        double total = 0.0;

        // Procesa cada item de la orden
        for (OrderItem item : order.getOrderItems()) {

            // Obtiene el producto y lanza excepción si no existe
            Product product = productService.getProductById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Producto no existe: " + item.getProduct().getId()));

            // Verifica que haya stock suficiente
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto: " + product.getProducto());
            }

            // Calcula precio usando la estrategia configurada
            double priceForProduct = pricingContext.calculate(product);
            total += priceForProduct * item.getQuantity();

            // Guarda el precio unitario en el item
            item.setUnitPrice(priceForProduct);

            // Asocia correctamente el producto y la orden al item
            item.setProduct(product);
            item.setOrder(order);

            // Notifica si el stock es bajo
            stockObserverService.checkStock(product);
        }

        // Asigna el total calculado a la orden
        order.setTotalAmount(total);

        // Descontamos stock de los productos de la orden al crear
        descontarStock(order);

        // Asocia el payment con la orden si existe
        if (order.getPayment() != null) {
            order.getPayment().setOrder(order);
        }

        // Guarda y retorna la orden
        return orderRepository.save(order);
    }

    // Actualiza el estado de la orden y maneja el stock según corresponda
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {

        // Obtiene la orden y lanza excepción si no existe
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + orderId));

        // Si se entrega, descontar stock solo si aún no se ha descontado
        if (status == OrderStatus.ENTREGADO && !order.isStockDescontado()) {
            descontarStock(order);
        }

        // Si se cancela, restaurar stock solo si previamente se había descontado
        if (status == OrderStatus.CANCELADO && order.isStockDescontado()) {
            restaurarStock(order);
        }

        // Actualiza el estado de la orden
        order.setStatus(status);

        return orderRepository.save(order);
    }

    // -------- Métodos auxiliares privados --------

    // Descontar stock de los productos de la orden
    private void descontarStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {

            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para el producto: " + product.getProducto());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productService.saveProduct(product);
        }

        order.setStockDescontado(true);
    }

    // Restaurar stock de los productos de la orden
    private void restaurarStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productService.saveProduct(product);
        }
        order.setStockDescontado(false);
    }
}
