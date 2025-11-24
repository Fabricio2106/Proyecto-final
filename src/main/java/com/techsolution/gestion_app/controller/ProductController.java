package com.techsolution.gestion_app.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.common.exception.InsufficientStockException;
import com.techsolution.gestion_app.common.exception.OrderNotFoundException;
import com.techsolution.gestion_app.common.exception.ProductNotFoundException;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.service.ProductService;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //se crear un producto
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.ok(saved);
    }
    //obtiene todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    //busca un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //actualiza un producto
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product data) {
        Product updated = productService.updateProduct(id, data);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
    //elimina un producto
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    //devuelve ProductNotFoundException
    @GetMapping("/test/product-not-found")
    public void testProductNotFound() {
        throw new ProductNotFoundException("Producto de prueba no encontrado");
    }
    //devuelve OrderNotFoundException
    @GetMapping("/test/order-not-found")
    public void testOrderNotFound() {
        throw new OrderNotFoundException("Orden de prueba no encontrada");
    }
    //devuelve InsufficientStockException
    @GetMapping("/test/insufficient-stock")
    public void testInsufficientStock() {
        throw new InsufficientStockException("Stock insuficiente para la prueba");
    }
    //devuelve excepción genérica
    @GetMapping("/test/generic-error")
    public void testGenericError() {
        throw new RuntimeException("Error inesperado de prueba");
    }
}
