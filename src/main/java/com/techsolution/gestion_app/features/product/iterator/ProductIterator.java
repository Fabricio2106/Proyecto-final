package com.techsolution.gestion_app.features.product.iterator;

import com.techsolution.gestion_app.domain.entities.Product;

public interface ProductIterator {
    boolean hasNext();
    Product next();
}
