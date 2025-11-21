package com.techsolution.gestion_app.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // unidades del producto que se están comprando
    private Integer quantity;
    // cada item pertenece a una sola orden (relación muchos a uno)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    //referencia al producto que se está añadiendo a la orden
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}