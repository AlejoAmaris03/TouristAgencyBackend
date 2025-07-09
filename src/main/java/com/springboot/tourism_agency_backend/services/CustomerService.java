package com.springboot.tourism_agency_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.dto.CustomerDTO;
import com.springboot.tourism_agency_backend.models.Customer;
import com.springboot.tourism_agency_backend.repositories.CustomerRepository;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<CustomerDTO> getCustomers() {
        return this.customerRepo.findAllByOrderById();
    }

    public CustomerDTO getCustomerById(int customerId) {
        Customer customer = this.customerRepo.findById(customerId).get();

        return this.getCustomerDTO(customer);
    }

    public CustomerDTO getCustomerByDni(Long customerDni) {
        return this.customerRepo.findByDni(customerDni);
    }

    public CustomerDTO addOrUpdateCustomer(Customer customer, String action) {
        switch (action) {
            case "add": {
                if(this.userRepo.findUserByDniOrUsername(customer.getDni(), customer.getUsername()) == null) {
                    customer.setPassword(passwordEncoder.encode(customer.getPassword()));
                    customerRepo.save(customer);

                    return this.getCustomerDTO(customer);
                }

                break;
            }
        
            case "update": {
                Customer currentCustomer = this.customerRepo.findById(customer.getId()).get();

                if(this.userRepo.findUserByDniOrUsernameExcludingCurrentOne(
                    customer.getId(), customer.getDni(), customer.getUsername()) == null) {

                    currentCustomer.setAddress(customer.getAddress());
                    currentCustomer.setDateOfBirth(customer.getDateOfBirth());
                    currentCustomer.setDni(customer.getDni());
                    currentCustomer.setEmail(customer.getEmail());
                    currentCustomer.setName(customer.getName());
                    currentCustomer.setNationality(customer.getNationality());
                    currentCustomer.setPhoneNumber(customer.getPhoneNumber());
                    currentCustomer.setSurname(customer.getSurname());

                    if(customer.getUsername() != null && !customer.getUsername().isEmpty())
                        currentCustomer.setUsername(customer.getUsername());

                    if(customer.getPassword() != null && !customer.getPassword().isEmpty())
                        currentCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));

                    customerRepo.save(currentCustomer);
                    return this.getCustomerDTO(customer);
                }

                break;
            }
        }

        return null;
    }

    public CustomerDTO updateInfo(Customer customer) {
        Customer currentCustomer = this.customerRepo.findById(customer.getId()).get();
        
        if(this.userRepo.findUserByDniOrUsernameExcludingCurrentOne(
            customer.getId(), customer.getDni(), customer.getUsername()) == null) {

            currentCustomer.setAddress(customer.getAddress());
            currentCustomer.setDateOfBirth(customer.getDateOfBirth());
            currentCustomer.setDni(customer.getDni());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setName(customer.getName());
            currentCustomer.setNationality(customer.getNationality());
            currentCustomer.setPhoneNumber(customer.getPhoneNumber());
            currentCustomer.setSurname(customer.getSurname());

            if(customer.getUsername() != null && !customer.getUsername().isEmpty())
                currentCustomer.setUsername(customer.getUsername());

            if(customer.getPassword() != null && !customer.getPassword().isEmpty())
                currentCustomer.setPassword(passwordEncoder.encode(customer.getPassword()));

            customerRepo.save(currentCustomer);
            return this.getCustomerDTO(currentCustomer);
        }

        return null;
    }

    public CustomerDTO deleteCustomer(int customerId) {
        try {
            Customer customer = this.customerRepo.findById(customerId).get();
            this.customerRepo.deleteById(customerId);

            return getCustomerDTO(customer);
        }
        catch (Exception e) {
            return null;
        }
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        return new CustomerDTO(
            customer.getId(),
            customer.getName(),
            customer.getSurname(),
            customer.getAddress(),
            customer.getDni(),
            customer.getDateOfBirth(),
            customer.getNationality(),
            customer.getPhoneNumber(),
            customer.getEmail(),
            customer.getRole()
        );
    }
}
