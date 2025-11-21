package com.techsolution.gestion_app.service;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.techsolution.gestion_app.domain.entities.Order;
import com.techsolution.gestion_app.domain.entities.OrderItem;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.domain.enums.OrderStatus;
import com.techsolution.gestion_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final StockObserverService stockObserverService;
    // Va a crear una nueva orden y calcular su total
    public Order createOrder(Order order) {
        // se inicializa el estado y la fecha
        order.setStatus(OrderStatus.PENDIENTE);
        order.setOrderDate(LocalDateTime.now());
        double total = 0;
        // va a recorrer todos los items del pedido
        for (OrderItem item : order.getOrderItems()) {
            // obtener el producto desde el servicio
            Product product = productService.getProductById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));
            //esto va validar stock disponible
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stock insuficiente: " + product.getProducto());
            }
            //esto va a descontar la cantidad reservada
            product.setStock(product.getStock() - item.getQuantity());
            productService.saveProduct(product);
            // va a sumar al monto total del pedido
            total += product.getPrecio() * item.getQuantity();
            // va a notificar en caso de que el producto quede con poco stock
            stockObserverService.checkStock(product);
        }
        order.setTotalAmount(total);
        // va a guardar la orden finalizada
        return orderRepository.save(order);
    }
    // va a cambiar el estado de un pedido específico
    public Order changeStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
    // va aobtener un pedido por su ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}
