package com.springboot.tourism_agency_backend.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.tourism_agency_backend.models.TourPackage;
import com.springboot.tourism_agency_backend.models.TouristService;
import com.springboot.tourism_agency_backend.repositories.TourPackageRepository;
import com.springboot.tourism_agency_backend.repositories.TouristServiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service

public class TourPackageService {
    @Autowired
    private TourPackageRepository tourPackageRepo;

    @Autowired
    private TouristServiceRepository touristServiceRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<TourPackage> getTourPackages() {
        return this.tourPackageRepo.findAllByOrderById();       
    }

    @Transactional
    public TourPackage saveTourPackage(TourPackage tourPackage) {
        if(!this.areServicesOrNameRepeated(tourPackage.getName(), tourPackage.getTouristServices())) {
            Set<TouristService> touristServices = tourPackage.getTouristServices().stream()
                .map(TS -> this.touristServiceRepo.findById(TS.getId()).get()).collect(Collectors.toSet());

            tourPackage.setTouristServices(touristServices);
            tourPackage.setPrice(getTotalPrice(tourPackage.getTouristServices()));
            return this.tourPackageRepo.save(tourPackage);
        }

        return null;
    }

    @Transactional
    public TourPackage updateTourPackageName(String tourPackageId, String name) {
        TourPackage tourPackage = this.tourPackageRepo.findById(Integer.parseInt(tourPackageId)).get();

        if(this.tourPackageRepo.findTourPackageByNameExcludingCurrentOne(tourPackage.getId(), name.toLowerCase()) == null) {
            tourPackage.setName(name);
            return this.tourPackageRepo.save(tourPackage);
        }

        return null;
    }

    @Transactional
    public TourPackage addServiceToPackage(TourPackage tourPackage) {
        if(!this.areServicesOrNameRepeated("", tourPackage.getTouristServices())) {
            tourPackage.setPrice(getTotalPrice(tourPackage.getTouristServices()));
            return this.tourPackageRepo.save(tourPackage);
        }

        return null;
    }

    @Transactional
    public TourPackage removeServiceFromPackage(String tourPackageId, String touristServiceId) {
        TourPackage tourPackage = this.tourPackageRepo.findById(Integer.parseInt(tourPackageId)).get();
        TouristService touristService = this.touristServiceRepo.findById(Integer.parseInt(touristServiceId)).get();

        this.entityManager.detach(tourPackage);
        tourPackage.getTouristServices().remove(touristService);

        if(!this.areServicesOrNameRepeated("", tourPackage.getTouristServices())) {
            if(tourPackage.getTouristServices().size() < 2) {
                this.tourPackageRepo.delete(tourPackage);
                return tourPackage;
            }

            tourPackage.setPrice(this.getTotalPrice(tourPackage.getTouristServices()));
            return this.tourPackageRepo.save(tourPackage);
        }

        return null;
    }

    public TourPackage deletePackage(int tourPackageId) {
        try {
            TourPackage tourPackage = this.tourPackageRepo.findById(tourPackageId).get();
            this.tourPackageRepo.deleteById(tourPackageId);

            return tourPackage;
        } 
        catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void updatePrice(TouristService service) {
        List<TourPackage> tourPackages = this.tourPackageRepo.findAllByOrderById();

        for(TourPackage tourPackage: tourPackages) {
            if(tourPackage.getTouristServices().contains(service)) {
                tourPackage.setPrice(this.getTotalPrice(tourPackage.getTouristServices()));
                this.tourPackageRepo.save(tourPackage);
            }
        }
    }

    private boolean areServicesOrNameRepeated(String tourPackageName, Set<TouristService> touristServices) {
        List<TourPackage> tourPackages = this.tourPackageRepo.findAllByOrderById();
        Set<Integer> touristServicesIds = touristServices.stream()
            .map(TS -> TS.getId()).collect(Collectors.toSet());

        for(TourPackage tourPackageList : tourPackages) {
            Set<Integer> servicesIds = tourPackageList.getTouristServices().stream()
                .map(TS -> TS.getId()).collect(Collectors.toSet());

            if(servicesIds.equals(touristServicesIds) || 
                tourPackageList.getName().toLowerCase().equals(tourPackageName.toLowerCase()))
                return true;
        }

        return false;
    }

    private Long getTotalPrice(Set<TouristService> touristServices) {
        Long sum = touristServices.stream().mapToLong(TS -> TS.getPrice()).sum();
        Long total = sum - ((long) (sum * .1));

        return total;
    }
}
