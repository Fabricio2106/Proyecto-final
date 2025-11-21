package com.techsolution.gestion_app.domain.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String producto;
    private String descripción;
    private Double precio;
    private Integer stock;
    private String categoria;

 public Product() {
        //Dejamos este constructor vacío para JPA 
    }
public Product(String producto, String descripción, Double precio, Integer stock, String categoria) {
        this.producto = producto;
        this.descripción = descripción;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
//gatter : devuelven el valor
//setters : modifica el valor

public Long getId() {
        return id;
    }
    @NotBlank
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    @NotBlank
    public String getDescripción() {
        return descripción;
    }
    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
    @NotNull
    @Min(1)
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    @NotNull
    @Min(0)
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    @NotBlank
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
}