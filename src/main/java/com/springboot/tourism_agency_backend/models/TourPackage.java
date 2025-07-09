package com.springboot.tourism_agency_backend.models;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour_packages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long price;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
        name = "tour_packages_services",
        joinColumns = @JoinColumn(name = "package_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<TouristService> touristServices;
}
