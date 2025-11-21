package com.techsolution.gestion_app.controller;
import com.techsolution.gestion_app.domain.entities.Customer;
import com.techsolution.gestion_app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer UpdatCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        customer.setNombre(UpdatCustomer.getNombre());
        customer.setCorreo(UpdatCustomer.getCorreo());
        return customerRepository.save(customer);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

}
