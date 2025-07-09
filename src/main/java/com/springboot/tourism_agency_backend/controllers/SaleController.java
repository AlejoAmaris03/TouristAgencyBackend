package com.springboot.tourism_agency_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.dto.SaleDTO;
import com.springboot.tourism_agency_backend.dto.SaleDetailsDTO;
import com.springboot.tourism_agency_backend.dto.SalesDoneDTO;
import com.springboot.tourism_agency_backend.models.Sale;
import com.springboot.tourism_agency_backend.services.SaleService;

@CrossOrigin
@RestController
@RequestMapping("/sales")

public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleDetailsDTO>> getSales() {
        return ResponseEntity.ok(this.saleService.getSales());
    }

    @GetMapping("/find/sale/{saleId}")
    public ResponseEntity<SaleDetailsDTO> getSaleById(@PathVariable int saleId) {
        return ResponseEntity.ok(this.saleService.getSaleById(saleId));
    }

    @GetMapping("/find/purchases/{customerDni}")
    public ResponseEntity<List<SaleDTO>> getPurchasesByCustomerDni(@PathVariable Long customerDni) {
        return ResponseEntity.ok(this.saleService.getPurchasesByCustomerDni(customerDni));
    }

    @GetMapping("/find/sales/{employeeDni}")
    public ResponseEntity<List<SalesDoneDTO>> getSalesByEmployeeDni(@PathVariable Long employeeDni) {
        return ResponseEntity.ok(this.saleService.getSalesByEmployeeDni(employeeDni));
    }

    @GetMapping("/find/buyers/{serviceId}")
    public ResponseEntity<List<SaleDetailsDTO>> getBuyersByServiceId(@PathVariable int serviceId) {
        return ResponseEntity.ok(this.saleService.getBuyersByServiceId(serviceId));
    }

    @GetMapping("/find/purchasers/{packageId}")
    public ResponseEntity<List<SaleDetailsDTO>> getBuyersByPackageId(@PathVariable int packageId) {
        return ResponseEntity.ok(this.saleService.getBuyersByPackageId(packageId));
    }

    @PostMapping("/buyServiceOrPackage")
    public ResponseEntity<SaleDTO> buyServiceOrPackage(@RequestBody Sale sale) {
        return ResponseEntity.ok(this.saleService.buyServiceOrPackage(sale));
    }

    @DeleteMapping("/delete/{purchaseId}")
    public ResponseEntity<SaleDTO> deletePurchase(@PathVariable int purchaseId) {
        return ResponseEntity.ok(this.saleService.deletePurchase(purchaseId));
    }
}
