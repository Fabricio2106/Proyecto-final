package com.techsolution.gestion_app.service;

import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.repository.CustomerRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// servicio para manejar la lógica de clientes
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository; // repositorio de clientes

    // busca un cliente por su ID
    // lanza una excepción si no existe
    public Customer getCustomerById(@NonNull Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));
    }

    // guarda un cliente nuevo o actualiza uno existente
    public Customer saveCustomer(@NonNull Customer customer) {
        return customerRepository.save(customer);
    }
}
