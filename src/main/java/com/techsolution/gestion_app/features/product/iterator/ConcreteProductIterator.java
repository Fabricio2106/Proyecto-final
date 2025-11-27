package com.techsolution.gestion_app.features.product.iterator;

import java.util.List;

import com.techsolution.gestion_app.domain.entities.Product;

public class ConcreteProductIterator implements ProductIterator {

    private int index = 0;
    private final List<Product> products;

    public ConcreteProductIterator(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean hasNext() {
        return index < products.size();
    }

    @Override
    public Product next() {
        return products.get(index++);
    }
}
