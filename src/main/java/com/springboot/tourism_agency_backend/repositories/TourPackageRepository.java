package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.TourPackage;

@Repository

public interface TourPackageRepository extends JpaRepository<TourPackage, Integer>{
    public List<TourPackage> findAllByOrderById();

    @Query(
    """
        SELECT TP
        FROM TourPackage TP
        WHERE (TP.id != ?1) AND (LOWER(TP.name) = ?2)
    """)
    public TourPackage findTourPackageByNameExcludingCurrentOne(int tourPackageId, String newName);
}
