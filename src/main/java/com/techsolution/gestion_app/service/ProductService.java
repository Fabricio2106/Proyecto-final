package com.techsolution.gestion_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.repository.ProductRepository;

import lombok.NonNull;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // constructor spring se encarga de inyectar la dependencia
    public ProductService(@NonNull ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // va a listar todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // esto va a buscar los productos por id
    public Optional<Product> getProductById(@NonNull Long id) {
        return productRepository.findById(id);
    }

    // va a registrar un nuevo producto
    public Product saveProduct(@NonNull Product product) {
        return productRepository.save(product);
    }

    // esto va a eliminar un producto por id
    public void deleteProduct(@NonNull Long id) {
        productRepository.deleteById(id);
    }

    // va a actualizar un producto existente
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

        return productRepository.save(product);
    }
}
