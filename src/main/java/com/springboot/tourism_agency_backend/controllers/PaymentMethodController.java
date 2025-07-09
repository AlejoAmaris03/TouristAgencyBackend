package com.springboot.tourism_agency_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.models.PaymentMethod;
import com.springboot.tourism_agency_backend.services.PaymentMethodService;

@CrossOrigin
@RestController
@RequestMapping("/paymentMethods")

public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods() {
        return ResponseEntity.ok(this.paymentMethodService.getPaymentMethods());
    }

    @GetMapping("/find/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable int paymentMethodId) {
        return ResponseEntity.ok(this.paymentMethodService.getPaymentMethodById(paymentMethodId));
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<PaymentMethod> saveOrUpdate(@RequestPart PaymentMethod paymentMethod, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.paymentMethodService.saveOrUpdate(paymentMethod, action));
    }

    @DeleteMapping("/delete/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> deletePaymentMethod(@PathVariable int paymentMethodId) {
        return ResponseEntity.ok(this.paymentMethodService.deletePaymentMethod(paymentMethodId));
    }
}
