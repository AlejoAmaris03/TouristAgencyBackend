package com.springboot.tourism_agency_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.dto.CustomerDTO;
import com.springboot.tourism_agency_backend.dto.UserSessionDTO;
import com.springboot.tourism_agency_backend.models.Customer;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.services.AuthenticationService;

@CrossOrigin
@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/authenticate")
    public ResponseEntity<UserSessionDTO> authenticate(@RequestBody Users user) {
        return ResponseEntity.ok(this.authenticationService.authenticate(user));
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registered(@RequestBody Customer customer) {
        return ResponseEntity.ok(this.authenticationService.register(customer));
    }
}
