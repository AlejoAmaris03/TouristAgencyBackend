package com.springboot.tourism_agency_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Users saveUser(Users user) {
        if(userRepo.findUserByDniOrUsername(user.getDni(), user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }

        return null;
    }

}
