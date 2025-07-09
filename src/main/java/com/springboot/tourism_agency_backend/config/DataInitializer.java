package com.springboot.tourism_agency_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springboot.tourism_agency_backend.models.Role;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.RoleRepository;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Configuration

public class DataInitializer {
    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepo) {
        // Default roles
        return roles -> {
            if(roleRepo.findByName("ROLE_ADMIN") == null)
                roleRepo.save(new Role(0, "ROLE_ADMIN"));

            if(roleRepo.findByName("ROLE_CUSTOMER") == null)
                roleRepo.save(new Role(0, "ROLE_CUSTOMER"));

            if(roleRepo.findByName("ROLE_EMPLOYEE") == null)
                roleRepo.save(new Role(0, "ROLE_EMPLOYEE"));
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initUser(UserRepository userRepo) {
        // Default admin acc
        return user -> {
            if(userRepo.findUserByDniOrUsername(112223344L, "admin") == null) {
                userRepo.save(new Users(
                    0,
                    "Admin name",
                    "Admin surname",
                    112223344L,
                    "admin@example.com",
                    "admin",
                    "admin",
                    new Role(1, "ROLE_ADMIN")
                ));
            }
        };
    }
}
