package com.techsolution.gestion_app.features.product.iterator;

import java.util.List;

import com.techsolution.gestion_app.domain.entities.Product;

public class ProductCollection {

    private final List<Product> products;

    public ProductCollection(List<Product> products) {
        this.products = products;
    }

    public ProductIterator createIterator() {
        return new ConcreteProductIterator(products);
    }
}
