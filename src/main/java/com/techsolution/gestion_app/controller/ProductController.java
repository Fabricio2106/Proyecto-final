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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.common.exception.InsufficientStockException;
import com.techsolution.gestion_app.common.exception.OrderNotFoundException;
import com.techsolution.gestion_app.common.exception.ProductNotFoundException;
import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.service.ProductService;
import com.techsolution.gestion_app.service.StockObserverService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final StockObserverService stockObserverService;

    public ProductController(ProductService productService, StockObserverService stockObserverService) {
        this.productService = productService;
        this.stockObserverService = stockObserverService;
    }

    // crear un producto
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.ok(saved);
    }

    // obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // buscar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product data) {
        // lanzamos excepción si el producto no existe
        Product updated = productService.updateProduct(id, data);
        if (updated == null) {
            throw new ProductNotFoundException("Producto no encontrado con ID " + id);
        }
        return ResponseEntity.ok(updated);
    }

    // eliminar un producto
@DeleteMapping("/{id}")
public ResponseEntity<String> delete(@PathVariable Long id) {
    try {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID " + id));

        productService.deleteProduct(id); // aquí sigue siendo void
        return ResponseEntity.ok("Producto eliminado correctamente");
    } catch (ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    } catch (Exception ex) {
        // captura errores inesperados y evita 500 genérico
        return ResponseEntity.status(500).body("Error al eliminar el producto: " + ex.getMessage());
    }
}

    // actualiza el stock y muestra las alertas
    @PutMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam int stock) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID " + id));

        // validamos que el stock no sea negativo
        if (stock < 0) {
            throw new InsufficientStockException("El stock no puede ser negativo");
        }

        // actualizamos el stock y avisamos a GERENTE y COMPRAS si es necesario
        stockObserverService.updateStock(product, stock);

        return ResponseEntity.ok("Stock actualizado a " + product.getStock() + " y alertas enviadas si corresponde");
    }

    // endpoints de prueba para manejar errores
    @GetMapping("/test/product-not-found")
    public void testProductNotFound() {
        throw new ProductNotFoundException("Producto de prueba no encontrado");
    }

    @GetMapping("/test/order-not-found")
    public void testOrderNotFound() {
        throw new OrderNotFoundException("Orden de prueba no encontrada");
    }

    @GetMapping("/test/insufficient-stock")
    public void testInsufficientStock() {
        throw new InsufficientStockException("Stock insuficiente para la prueba");
    }

    @GetMapping("/test/generic-error")
    public void testGenericError() {
        throw new RuntimeException("Error inesperado de prueba");
    }
}
