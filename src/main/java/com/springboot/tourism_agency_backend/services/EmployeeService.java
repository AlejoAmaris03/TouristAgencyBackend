package com.springboot.tourism_agency_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.dto.EmployeeDTO;
import com.springboot.tourism_agency_backend.models.Employee;
import com.springboot.tourism_agency_backend.repositories.EmployeeRepository;
import com.springboot.tourism_agency_backend.repositories.UserRepository;

@Service

public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<EmployeeDTO> getEmployees() {
        return this.employeeRepo.findAllByOrderById();
    }

    public EmployeeDTO getEmployeeByEmployeeId(int customerId) {
        Employee employee = this.employeeRepo.findById(customerId).get();

        return this.getEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeByDni(Long employeeDni) {
        Employee employee = this.employeeRepo.findEmployeeByDni(employeeDni);

        return this.getEmployeeDTO(employee);
    }

    public EmployeeDTO saveOrUpdateEmployee(Employee employee, String action) {
        switch (action) {
            case "add": {
                if(this.userRepo.findUserByDniOrUsername(employee.getDni(), employee.getUsername()) == null) {
                    employee.setPassword(passwordEncoder.encode(employee.getPassword()));
                    employeeRepo.save(employee);

                    return this.getEmployeeDTO(employee);
                }

                break;
            }
        
            case "update": {
                Employee currentEmployee = this.employeeRepo.findById(employee.getId()).get();

                if(this.userRepo.findUserByDniOrUsernameExcludingCurrentOne(
                    employee.getId(), employee.getDni(), employee.getUsername()) == null) {

                    currentEmployee.setAddress(employee.getAddress());
                    currentEmployee.setDateOfBirth(employee.getDateOfBirth());
                    currentEmployee.setDni(employee.getDni());
                    currentEmployee.setEmail(employee.getEmail());
                    currentEmployee.setJobTitle(employee.getJobTitle());
                    currentEmployee.setName(employee.getName());
                    currentEmployee.setNationality(employee.getNationality());
                    currentEmployee.setPhoneNumber(employee.getPhoneNumber());
                    currentEmployee.setSalary(employee.getSalary());
                    currentEmployee.setSurname(employee.getSurname());

                    if(employee.getUsername() != null && !employee.getUsername().isEmpty())
                        currentEmployee.setUsername(employee.getUsername());

                    if(employee.getPassword() != null && !employee.getPassword().isEmpty())
                        currentEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));

                    this.employeeRepo.save(currentEmployee);
                    return this.getEmployeeDTO(employee);
                }

                break;
            }
        }

        return null;
    }

    public EmployeeDTO updateInfo(Employee employee) {
        Employee currentEmployee = this.employeeRepo.findById(employee.getId()).get();

        if(this.userRepo.findUserByDniOrUsernameExcludingCurrentOne(
            employee.getId(), employee.getDni(), employee.getUsername()) == null) {

            currentEmployee.setAddress(employee.getAddress());
            currentEmployee.setDateOfBirth(employee.getDateOfBirth());
            currentEmployee.setDni(employee.getDni());
            currentEmployee.setEmail(employee.getEmail());
            currentEmployee.setName(employee.getName());
            currentEmployee.setNationality(employee.getNationality());
            currentEmployee.setPhoneNumber(employee.getPhoneNumber());
            currentEmployee.setSurname(employee.getSurname());

            if(employee.getUsername() != null && !employee.getUsername().isEmpty())
                currentEmployee.setUsername(employee.getUsername());

            if(employee.getPassword() != null && !employee.getPassword().isEmpty())
                currentEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));

            this.employeeRepo.save(currentEmployee);
            return this.getEmployeeDTO(currentEmployee);
        }

        return null;
    }

    public EmployeeDTO deleteEmployee(int employeeId) {
        try {
            Employee employee = this.employeeRepo.findById(employeeId).get();
            this.employeeRepo.deleteById(employeeId);

            return getEmployeeDTO(employee);
        }
        catch (Exception e) {
            return null;
        }
    }

    private EmployeeDTO getEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
            employee.getId(),
            employee.getName(),
            employee.getSurname(),
            employee.getAddress(),
            employee.getDni(),
            employee.getDateOfBirth(),
            employee.getNationality(),
            employee.getPhoneNumber(),
            employee.getEmail(),
            employee.getJobTitle(),
            employee.getSalary(),
            employee.getRole()
        );
    }
}
