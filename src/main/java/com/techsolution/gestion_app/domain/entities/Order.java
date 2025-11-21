package com.techsolution.gestion_app.domain.entities;
import java.time.LocalDateTime;
import java.util.List;

import com.techsolution.gestion_app.domain.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private Double totalAmount;
    // Estado actual del pedido
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    // Muchos pedidos pueden pertenecer al mismo cliente
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    // Relación uno a uno con el pago
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    // Una orden puede tener varios items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
