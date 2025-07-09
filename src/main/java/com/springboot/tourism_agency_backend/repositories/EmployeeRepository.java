package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.dto.EmployeeDTO;
import com.springboot.tourism_agency_backend.models.Employee;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    public Employee findEmployeeByDni(Long employeeDni);

    @Query(
    """
       SELECT new com.springboot.tourism_agency_backend.dto.EmployeeDTO(
            e.id,
            e.name,
            e.surname,
            e.address,
            e.dni,
            e.dateOfBirth,
            e.nationality,
            e.phoneNumber,
            e.email,
            e.jobTitle,
            e.salary,
            e.role
       )
       FROM Employee e
       ORDER BY e.id     
    """)
    public List<EmployeeDTO> findAllByOrderById();
}
