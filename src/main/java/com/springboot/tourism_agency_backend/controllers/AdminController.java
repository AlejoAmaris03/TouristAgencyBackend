package com.springboot.tourism_agency_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.dto.AdminDTO;
import com.springboot.tourism_agency_backend.models.Users;
import com.springboot.tourism_agency_backend.services.AdminService;

@CrossOrigin
@RestController
@RequestMapping("/admins")

public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/find/{adminDni}")
    public ResponseEntity<AdminDTO> getAdminByDni(@PathVariable Long adminDni) {
        return ResponseEntity.ok(this.adminService.getAdminByDni(adminDni));
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<AdminDTO> updateInfo(@RequestBody Users admin) {
        return ResponseEntity.ok(this.adminService.updateInfo(admin));
    }
}
