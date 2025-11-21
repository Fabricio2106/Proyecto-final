//Nos va permitir conectar con la base de datos  y realizar operaciones CRUD sobre la entidad Product
package com.techsolution.gestion_app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techsolution.gestion_app.domain.entities.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}