package com.springboot.tourism_agency_backend.dto;

import java.time.LocalDate;
import com.springboot.tourism_agency_backend.models.JobTitle;
import com.springboot.tourism_agency_backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO {
    private int id;
    private String name;
    private String surname;
    private String address;
    private Long dni;
    private LocalDate dateOfBirth;
    private String nationality;
    private Long phoneNumber;
    private String email;
    private JobTitle jobTitle;
    private Long salary;
    private Role role;
}
