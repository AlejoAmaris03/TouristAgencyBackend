package com.springboot.tourism_agency_backend.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate dateOfSale;

    @ManyToOne
    @JoinColumn(
        name = "payment_method_id",
        nullable = false
    )
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(
        name = "customer_id",
        nullable = false
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
        name = "employee_id",
        nullable = false
    )
    private Employee employee;

    @ManyToOne
    @JoinColumn(
        name = "tourist_service_id",
        nullable = true
    )
    private TouristService touristService;

    @ManyToOne
    @JoinColumn(
        name = "tour_package_id",
        nullable = true
    )
    private TourPackage tourPackage;
}
