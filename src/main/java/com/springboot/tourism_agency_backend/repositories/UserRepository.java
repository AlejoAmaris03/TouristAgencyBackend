package com.springboot.tourism_agency_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.Users;

@Repository

public interface UserRepository extends JpaRepository<Users, Integer> {
    public Users findUserByDniOrUsername(Long dni, String username);
    public Users findUserByUsername(String username);
    public Users findUserByDni(Long dni);

    @Query(
    """
        SELECT u
        FROM Users u
        WHERE (u.id != ?1) AND (u.dni = ?2 OR u.username = ?3)        
    """)
    public Users findUserByDniOrUsernameExcludingCurrentOne(int currentUserId, Long dni, String username);
}
