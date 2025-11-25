package com.techsolution.gestion_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolution.gestion_app.domain.entities.PaymentConfig;

//repo para manejar la configuración de pasarelas.Como solo tendremos una configuración global,básicamente sirve para leerla y actualizarla cuando el admin cambie algo.
 
public interface PaymentConfigRepository extends JpaRepository<PaymentConfig, Long> {
}
