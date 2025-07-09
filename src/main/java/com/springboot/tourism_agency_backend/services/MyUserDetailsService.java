package com.springboot.tourism_agency_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.models.UserPrincipal;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findUserByUsername(username);   

        if(user == null)
            throw new UsernameNotFoundException("User not found with username: " + username);

        return new UserPrincipal(user);
    }
}
