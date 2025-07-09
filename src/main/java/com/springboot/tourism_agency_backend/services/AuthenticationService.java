package com.springboot.tourism_agency_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.dto.CustomerDTO;
import com.springboot.tourism_agency_backend.dto.UserSessionDTO;
import com.springboot.tourism_agency_backend.models.Customer;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.CustomerRepository;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class AuthenticationService {
    @Autowired
    private UserRepository userRepo;

    @Autowired 
    private CustomerRepository customerRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserSessionDTO authenticate(Users user) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if(authentication.isAuthenticated()) {
                Users authUser = this.userRepo.findUserByUsername(user.getUsername());
                UserSessionDTO userDTO = this.getUserSessionDTO(authUser);
                userDTO.setToken(this.jwtService.generateToken(authUser));

                return userDTO;
            }
            
            return null;
        } 
        catch(Exception e) {
            return null;
        }
    }

    public CustomerDTO register(Customer customer) {
        if(this.userRepo.findUserByDniOrUsername(customer.getDni(), customer.getUsername()) == null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerRepo.save(customer);

            return this.getCustomerDTO(customer);
        }

        return null;
    }

    private UserSessionDTO getUserSessionDTO(Users user) {
        return new UserSessionDTO(
            user.getDni(), 
            user.getName(), 
            user.getSurname(), 
            null
        );
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
