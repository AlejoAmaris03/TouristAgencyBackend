package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.Sale;

@Repository

public interface SaleRepository extends JpaRepository<Sale, Integer>{
    public List<Sale> findAllByOrderByIdAsc();
    public List<Sale> findAllByCustomerDni(Long customerDni);
    public List<Sale> findAllByEmployeeDni(Long employeeDni);
    public List<Sale> findAllByTouristServiceId(int serviceId);
    public List<Sale> findAllByTourPackageId(int packageId);

    @Query(
    """
        SELECT s
        FROM Sale s
        WHERE s.customer.id = ?1 AND s.touristService.id = ?2 AND s.touristService.date >= CURRENT_DATE
    """)
    public Sale checkIfPurchasedServiceIsRepeated(int customerId, int serviceId);

    @Query(
    """
        SELECT s
        FROM Sale s
        WHERE s.customer.id = ?1 AND s.tourPackage.id = ?2
    """)
    public Sale checkIfPurchasedPackageIsRepeated(int customerId, int packageId);
}
