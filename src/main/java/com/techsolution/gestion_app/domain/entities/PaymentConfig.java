package com.techsolution.gestion_app.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// Configuración de pasarelas de pago en el sistema
@Entity
public class PaymentConfig {
    @Id
    private final Long id = 1L; 
    // Solo habrá un registro global, por eso el ID es fijo y final
    // Estado de cada pasarela
    private boolean paypalActivo = true;
    private boolean yapeActivo = true;
    private boolean plinActivo = true;
    // Getters y setters simples
    public Long getId() {
        return id;
    }
    public boolean isPaypalActivo() {
        return paypalActivo;
    }
    public void setPaypalActivo(boolean paypalActivo) {
        this.paypalActivo = paypalActivo;
    }
    public boolean isYapeActivo() {
        return yapeActivo;
    }
    public void setYapeActivo(boolean yapeActivo) {
        this.yapeActivo = yapeActivo;
    }
    public boolean isPlinActivo() {
        return plinActivo;
    }
    public void setPlinActivo(boolean plinActivo) {
        this.plinActivo = plinActivo;
    }
}
