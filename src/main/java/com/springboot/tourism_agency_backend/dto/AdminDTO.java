package com.springboot.tourism_agency_backend.dto;

import com.springboot.tourism_agency_backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdminDTO {
    private int id;
    private Long dni;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
