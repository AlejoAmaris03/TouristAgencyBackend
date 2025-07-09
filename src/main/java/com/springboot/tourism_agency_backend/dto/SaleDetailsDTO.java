package com.springboot.tourism_agency_backend.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SaleDetailsDTO {
    private int id;
    private String description;
    private String destination;
    private LocalDate dateOfSale;
    private LocalDate date;
    private String paymentMethod;
    private String touristServiceName;
    private String tourPackageName;
    private List<String> servicesIncluded;
    private String customerName;
    private String employeeName;
    private Long price;
}
