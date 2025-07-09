package com.springboot.tourism_agency_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.dto.CustomerDTO;
import com.springboot.tourism_agency_backend.models.Customer;
import com.springboot.tourism_agency_backend.services.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/customers")

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(this.customerService.getCustomers());
    }

    @GetMapping("/find/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
    }

    @GetMapping("/findByDni/{customerDni}")
    public ResponseEntity<CustomerDTO> getCustomerByDni(@PathVariable Long customerDni) {
        return ResponseEntity.ok(this.customerService.getCustomerByDni(customerDni));
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<CustomerDTO> saveOrUpdateCustomer(@RequestPart Customer customer, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.customerService.addOrUpdateCustomer(customer, action));
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<CustomerDTO> updateInfo(@RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerService.updateInfo(customer));
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(this.customerService.deleteCustomer(customerId));
    }
}
