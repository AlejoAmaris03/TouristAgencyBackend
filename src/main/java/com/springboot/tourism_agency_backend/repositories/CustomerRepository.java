package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.dto.CustomerDTO;
import com.springboot.tourism_agency_backend.models.Customer;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(
    """
       SELECT new com.springboot.tourism_agency_backend.dto.CustomerDTO(
            c.id,
            c.name,
            c.surname,
            c.address,
            c.dni,
            c.dateOfBirth,
            c.nationality,
            c.phoneNumber,
            c.email,
            c.role
       )
       FROM Customer c
       ORDER BY c.id     
    """)
    public List<CustomerDTO> findAllByOrderById();

   @Query(
    """
      SELECT new com.springboot.tourism_agency_backend.dto.CustomerDTO(
         c.id,
         c.name,
         c.surname,
         c.address,
         c.dni,
         c.dateOfBirth,
         c.nationality,
         c.phoneNumber,
         c.email,
         c.role
      )
      FROM Customer c
      WHERE c.dni = ?1
   """)
   public CustomerDTO findByDni(Long dni);
}
