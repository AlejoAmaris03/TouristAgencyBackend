package com.springboot.tourism_agency_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.dto.AdminDTO;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class AdminService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AdminDTO getAdminByDni(Long dni) {
        Users admin = this.userRepo.findUserByDni(dni);

        return this.getAdminDTO(admin);
    }

    public AdminDTO updateInfo(Users admin) {
        Users currentAdmin = this.userRepo.findById(admin.getId()).get();

        if(this.userRepo.findUserByDniOrUsernameExcludingCurrentOne(
            admin.getId(), admin.getDni(), admin.getUsername()) == null) {

            currentAdmin.setDni(admin.getDni());
            currentAdmin.setEmail(admin.getEmail());
            currentAdmin.setName(admin.getName());
            currentAdmin.setSurname(admin.getSurname());

            if(admin.getUsername() != null && !admin.getUsername().isEmpty())
                currentAdmin.setUsername(admin.getUsername());

            if(admin.getPassword() != null && !admin.getPassword().isEmpty())
                currentAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));

            userRepo.save(currentAdmin);
            return this.getAdminDTO(currentAdmin);
        }

        return null;
    }

    private AdminDTO getAdminDTO(Users admin) {
        return new AdminDTO(
            admin.getId(),
            admin.getDni(),
            admin.getName(),
            admin.getSurname(),
            admin.getEmail(),
            admin.getRole()
        );
    }
}
