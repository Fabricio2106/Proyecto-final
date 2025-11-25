package com.techsolution.gestion_app.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    // inyectamos el repositorio para acceder a la base de datos
    private final CustomerRepository customerRepository;
    // obtiene todos los clientes
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // devolvemos lista completa
    }
    // obtiene un cliente por su ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        // si no lo encuentra, lanzamos excepción simple
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    // crear un nuevo cliente
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer); // guardamos en BD y devolvemos
    }
    // actualizar datos de un cliente existente
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer UpdatCustomer) {
        // buscamos el cliente
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        // modificamos los campos que nos pasan
        customer.setNombre(UpdatCustomer.getNombre());
        customer.setCorreo(UpdatCustomer.getCorreo());
        // guardamos cambios
        return customerRepository.save(customer);
    }
    // eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id); // borramos de la BD
    }
}
