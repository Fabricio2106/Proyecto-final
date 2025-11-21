package com.techsolution.gestion_app.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Product;
import com.techsolution.gestion_app.repository.ProductRepository;
@Service
public class ProductService {
    private final ProductRepository productRepository;
//Constructor spring se encarga de inyectar la dependencia
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
//va a listar todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//esto va a buscar los productos por id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
//va a registrar un nuevo producto
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
//
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
//va a actualizar un producto existente
    public Product updateProduct(Long id, Product data) {
        Optional<Product> original = productRepository.findById(id);
        if(original.isEmpty()){
            return null;
        }
        Product product= original.get();
        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setStock(data.getStock());
        product.setCategory(data.getCategory());
        return productRepository.save(product);
    }
}