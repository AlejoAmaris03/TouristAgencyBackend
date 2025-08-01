package com.springboot.tourism_agency_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Integer>{
    public Role findByName(String name);
}
