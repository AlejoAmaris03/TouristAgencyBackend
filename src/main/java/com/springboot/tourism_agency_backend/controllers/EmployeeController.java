package com.springboot.tourism_agency_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.dto.EmployeeDTO;
import com.springboot.tourism_agency_backend.models.Employee;
import com.springboot.tourism_agency_backend.services.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/employees")

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        return ResponseEntity.ok(this.employeeService.getEmployees());
    }

    @GetMapping("/find/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmployeeId(@PathVariable int employeeId) {
        return ResponseEntity.ok(this.employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @GetMapping("/find/byDni/{employeeDni}")
    public ResponseEntity<EmployeeDTO> getEmployeeByDni(@PathVariable Long employeeDni) {
        return ResponseEntity.ok(this.employeeService.getEmployeeByDni(employeeDni));
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<EmployeeDTO> saveOrUpdateEmployee(@RequestPart Employee employee, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.employeeService.saveOrUpdateEmployee(employee, action));
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<EmployeeDTO> updateInfo(@RequestBody Employee employee) {
        return ResponseEntity.ok(this.employeeService.updateInfo(employee));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(this.employeeService.deleteEmployee(employeeId));
    }
}
