package com.techsolution.gestion_app.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techsolution.gestion_app.domain.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // monto total que se pagó por la orden
    private Double amount;

    // estado del pago
    private PaymentStatus status;

    // relación con la orden a la que pertenece este pago
    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference(value = "order-payment")
    private Order order;
}
