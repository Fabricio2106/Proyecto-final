package com.techsolution.gestion_app.domain.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//representa a un cliente dentro del sistema aquí solo guardamos lo básico: su nombre y su email.más adelante se le pueden agregar direcciones, teléfono,historial de pedidos, etc.
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identifica el cliente y genera automáticamente la BD
    private String nombre;  // nombre completo del cliente
    private String correo; // correo electrónico del cliente
}
