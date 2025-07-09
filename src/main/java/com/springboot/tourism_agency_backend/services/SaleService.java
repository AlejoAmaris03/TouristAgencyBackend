package com.springboot.tourism_agency_backend.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.tourism_agency_backend.dto.SaleDTO;
import com.springboot.tourism_agency_backend.dto.SaleDetailsDTO;
import com.springboot.tourism_agency_backend.dto.SalesDoneDTO;
import com.springboot.tourism_agency_backend.models.Sale;
import com.springboot.tourism_agency_backend.repositories.SaleRepository;

@Service

public class SaleService {
    @Autowired
    private SaleRepository saleRepo;

    @Transactional
    public List<SaleDetailsDTO> getSales() {
        List<SaleDetailsDTO> sales = this.saleRepo.findAllByOrderByIdAsc()
            .stream()
            .map(s -> this.getSaleDetails(s))
            .toList();

        return sales;
    }

    @Transactional
    public List<SaleDTO> getPurchasesByCustomerDni(Long customerDni) {
        List<SaleDTO> purchases = this.saleRepo.findAllByCustomerDni(customerDni)
            .stream()
            .map(s -> this.getSaleDTO(s))
            .toList();

        return purchases;
    }

    @Transactional
    public List<SalesDoneDTO> getSalesByEmployeeDni(Long employeeDni) {
        List<SalesDoneDTO> sales = this.saleRepo.findAllByEmployeeDni(employeeDni)
            .stream()
            .map(s -> this.getSalesDone(s))
            .toList();

        return sales;
    }

    @Transactional
    public List<SaleDetailsDTO> getBuyersByServiceId(int serviceId) {
        List<SaleDetailsDTO> purchases = this.saleRepo.findAllByTouristServiceId(serviceId)
            .stream()
            .map(s -> this.getSaleDetails(s))
            .toList();

        return purchases;
    }

    @Transactional
    public List<SaleDetailsDTO> getBuyersByPackageId(int packageId) {
        List<SaleDetailsDTO> purchases = this.saleRepo.findAllByTourPackageId(packageId)
            .stream()
            .map(s -> this.getSaleDetails(s))
            .toList();

        return purchases;
    }

    @Transactional
    public SaleDetailsDTO getSaleById(int saleId) {
        Sale sale = this.saleRepo.findById(saleId).get();
        return this.getSaleDetails(sale);
    }

    @Transactional
    public SaleDTO buyServiceOrPackage(Sale sale) {
        LocalDate dateOfSale = LocalDate.now();

        if(sale.getTouristService() != null) {
            if(this.saleRepo.checkIfPurchasedServiceIsRepeated(sale.getCustomer().getId(), 
                sale.getTouristService().getId()) != null)
                return null;
        }
        else {
            if(this.saleRepo.checkIfPurchasedPackageIsRepeated(sale.getCustomer().getId(), 
                sale.getTourPackage().getId()) != null)
                return null;
        }

        sale.setDateOfSale(dateOfSale);
        this.saleRepo.save(sale);
        return this.getSaleDTO(sale);
    }

    public SaleDTO deletePurchase(int purchaseId) {
        Sale sale = this.saleRepo.findById(purchaseId).get();

        if(sale.getTouristService() != null) {
            int serviceDay = sale.getTouristService().getDate().getDayOfMonth();
            int currentDay = LocalDate.now().getDayOfMonth(); 
            int checkDay = serviceDay - currentDay;

            if(checkDay < 2)
                return null;
        }

        this.saleRepo.deleteById(purchaseId);
        return getSaleDTO(sale);
    }

    private SaleDTO getSaleDTO(Sale sale) {
        return new SaleDTO(
            sale.getId(),
            sale.getDateOfSale(),
            sale.getPaymentMethod().getName(),
            sale.getTouristService() != null ? sale.getTouristService().getName() : null,
            sale.getTourPackage() != null ? sale.getTourPackage().getName() : null,
            (sale.getCustomer().getName() + " " + sale.getCustomer().getSurname()).toString(),
            (sale.getEmployee().getName() + " " + sale.getEmployee().getSurname()).toString(),
            sale.getTouristService() != null ? sale.getTouristService().getPrice() : sale.getTourPackage().getPrice()
        );
    }

    private SaleDetailsDTO getSaleDetails(Sale sale) {
        return new SaleDetailsDTO(
            sale.getId(),
            sale.getTouristService() != null ? sale.getTouristService().getDescription() : null,
            sale.getTouristService() != null ? sale.getTouristService().getDestination() : null,
            sale.getDateOfSale(),
            sale.getTouristService() != null ? sale.getTouristService().getDate() : null,
            sale.getPaymentMethod().getName(),
            sale.getTouristService() != null ? sale.getTouristService().getName() : null,
            sale.getTourPackage() != null ? sale.getTourPackage().getName() : null,
            sale.getTourPackage() != null ? sale.getTourPackage().getTouristServices().stream().map(s -> s.getName()).toList(): null,
            (sale.getCustomer().getName() + " " + sale.getCustomer().getSurname()).toString(),
            (sale.getEmployee().getName() + " " + sale.getEmployee().getSurname()).toString(),
            sale.getTouristService() != null ? sale.getTouristService().getPrice() : sale.getTourPackage().getPrice()
        );
    }

    private SalesDoneDTO getSalesDone(Sale sale) {
        return new SalesDoneDTO(
            sale.getId(),
            sale.getDateOfSale(),
            sale.getPaymentMethod().getName(),
            sale.getTouristService() != null ? sale.getTouristService().getName() : null,
            sale.getTourPackage() != null ? sale.getTourPackage().getName() : null,
            (sale.getCustomer().getName() + " " + sale.getCustomer().getSurname()).toString(),
            sale.getTouristService() != null ? sale.getTouristService().getPrice() : sale.getTourPackage().getPrice(),
            sale.getPaymentMethod().getCommission(),
            sale.getTouristService() != null
                ? (long) (sale.getTouristService().getPrice() * sale.getPaymentMethod().getCommission() / 100)
                : (long) (sale.getTourPackage().getPrice() * sale.getPaymentMethod().getCommission() / 100)
        );
    }
}
