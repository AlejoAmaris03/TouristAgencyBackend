package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.PaymentMethod;

@Repository

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer>{
    public List<PaymentMethod> findAllByOrderById();
    public PaymentMethod findPaymentMethodByName(String name);

    @Query(
    """
       SELECT PM
       FROM PaymentMethod PM
       WHERE (PM.id != ?1) AND (PM.name = ?2)
    """)
    public PaymentMethod findPaymentMethodByNameExcludingCurrentOne(int paymentMethodId, String name);
}
