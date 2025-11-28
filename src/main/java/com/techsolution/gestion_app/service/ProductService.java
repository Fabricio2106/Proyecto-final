package com.techsolution.gestion_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.features.product.iterator.ProductCollection;
import com.techsolution.gestion_app.features.product.iterator.ProductIterator;
import com.techsolution.gestion_app.features.product.observer.ProductSubject;
import com.techsolution.gestion_app.repository.ProductRepository;

import lombok.NonNull;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSubject productSubject;

    // constructor con Subject para Observer
    public ProductService(
            @NonNull ProductRepository productRepository,
            @NonNull ProductSubject productSubject) {
        this.productRepository = productRepository;
        this.productSubject = productSubject;
    }

    // lista todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // iterator para recorrer catálogo (RF11 – RF12)
    public ProductIterator getProductIterator() {
        List<Product> products = productRepository.findAll();
        ProductCollection collection = new ProductCollection(products);
        return collection.createIterator();
    }

    // buscar por ID
    public Optional<Product> getProductById(@NonNull Long id) {
        return productRepository.findById(id);
    }

    // registar producto nuevo + OBSERVER
    public Product saveProduct(@NonNull Product product) {
        Product saved = productRepository.save(product);

        // si hay bajo stock, notificar (ahora pasamos el producto)
        if (saved.getStock() < saved.getStockMinimo()) {
            productSubject.notifyObservers(saved);  // ← notifica con el producto
        }

        return saved;
    }

    // eliminar un producto
    public void deleteProduct(@NonNull Long id) {
        productRepository.deleteById(id);
    }

    // actualizar producto existente + OBSERVER
    public Product updateProduct(@NonNull Long id, @NonNull Product data) {
        Optional<Product> original = productRepository.findById(id);
        if (original.isEmpty()) {
            return null;
        }

        Product product = original.get();
        product.setProducto(data.getProducto());
        product.setDescripción(data.getDescripción());
        product.setPrecio(data.getPrecio());
        product.setStock(data.getStock());
        product.setCategoria(data.getCategoria());
        product.setStockMinimo(data.getStockMinimo()); // RF6

        Product updated = productRepository.save(product);

        // si stock < mínimo → notificar
        if (updated.getStock() < updated.getStockMinimo()) {
            productSubject.notifyObservers(updated);  // ← notifica con el producto
        }

        return updated;
    }
}
