package com.springboot.tourism_agency_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.tourism_agency_backend.models.PaymentMethod;
import com.springboot.tourism_agency_backend.repositories.PaymentMethodRepository;

@Service

public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepo;

    public List<PaymentMethod> getPaymentMethods() {
        return this.paymentMethodRepo.findAllByOrderById();
    }

    public PaymentMethod getPaymentMethodById(int paymentMethodId) {
       return this.paymentMethodRepo.findById(paymentMethodId).get();
    }

    public PaymentMethod saveOrUpdate(PaymentMethod paymentMethod, String action) {
       switch (action) {
            case "add": {
                if(this.paymentMethodRepo.findPaymentMethodByName(paymentMethod.getName()) == null)
                    return this.paymentMethodRepo.save(paymentMethod);

                break;
            }
        
            case "update": {
                if(this.paymentMethodRepo.findPaymentMethodByNameExcludingCurrentOne(paymentMethod.getId(), paymentMethod.getName()) == null)
                    return this.paymentMethodRepo.save(paymentMethod);

                break;
            }
        }

        return null;
    }

    public PaymentMethod deletePaymentMethod(int paymentMethodId) {
        try {
            PaymentMethod paymentMethod = this.paymentMethodRepo.findById(paymentMethodId).get();
            this.paymentMethodRepo.deleteById(paymentMethodId);

            return paymentMethod;
        } 
        catch (Exception e) {
            return null;
        }
    }
}
