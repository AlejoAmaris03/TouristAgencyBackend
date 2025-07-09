package com.springboot.tourism_agency_backend.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SaleDTO {
    private int id;
    private LocalDate dateOfSale;
    private String paymentMethod;
    private String touristServiceName;
    private String tourPackageName;
    private String customerName;
    private String employeeName;
    private Long price;
}
