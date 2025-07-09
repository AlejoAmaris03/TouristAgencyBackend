package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.TouristService;

@Repository

public interface TouristServiceRepository extends JpaRepository<TouristService, Integer>{
    public List<TouristService> findAllByOrderById();
    public TouristService findTouristServiceByName(String name);

    @Query(
    """
        SELECT TS
        FROM TouristService TS
        WHERE (TS.id != ?1) AND (TS.name = ?2)
    """)
    public TouristService findTouristServiceByNameExcludingCurrentOne(int touristServiceId, String name);
}
