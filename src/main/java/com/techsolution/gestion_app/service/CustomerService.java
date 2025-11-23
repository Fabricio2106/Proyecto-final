package com.techsolution.gestion_app.service;
import org.springframework.stereotype.Service;

import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    //va a buscar un cliente por id
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}