package com.techsolution.gestion_app.service;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

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
    // Va a crear una nueva orden y calcular su total
    @Transactional
    public Order createOrder(Order order) {
        // se inicializa el estado y la fecha
        order.setStatus(OrderStatus.PENDIENTE);
        order.setOrderDate(LocalDateTime.now());
        // va a cargar cliente completo
        Customer customer = customerService.getCustomerById(order.getCustomer().getId());
        order.setCustomer(customer);
        // estrategia de precio
        pricingContext.setStrategy(new StandardPrice());
        double total = 0.0;
        // aca agregamos la logica donde va a procesar los items
        for (OrderItem item : order.getOrderItems()) {
            Product product = productService.getProductById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe: "));
            // verificamos stock disponible
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getProducto());
            }
            // calculo precio con la estrategia
            double priceForProduct = pricingContext.calculate(product);
            total += priceForProduct * item.getQuantity();
            // aca se va a guardar el campo unitPrice en ordenItem
            item.setUnitPrice(priceForProduct);
            // aca se va a asociar correctamente el producto y el pedido al item
            item.setProduct(product);
            item.setOrder(order);
            // esta parte del codigo va a notificar si el stock es bajo
            stockObserverService.checkStock(product);
        }
        // asignamos total calculado a la orden
        order.setTotalAmount(total);
        // esta es la parte donde se va asociar el payment con la orden
        if (order.getPayment() != null) {
            order.getPayment().setOrder(order);
        }
        // guarda la orden
        return orderRepository.save(order);
    }
    // si se cancela, va a devolver stock solo si ya se ha descontado
    // si se entrega, va a descontar stock solo una vez
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        // Si se entrega, descontar stock solo si no se ha descontado antes
        if (status == OrderStatus.ENTREGADO && !order.isStockDescontado()) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                if (product.getStock() < item.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + product.getProducto());
                }
                product.setStock(product.getStock() - item.getQuantity());
                productService.saveProduct(product);
            }
            order.setStockDescontado(true);
        }
        // Si se cancela, devolver stock solo si ya se había descontado
        if (status == OrderStatus.CANCELADO && order.isStockDescontado()) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productService.saveProduct(product);
            }
            order.setStockDescontado(false);
        }
        // actualiza el estado
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
