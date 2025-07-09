package com.springboot.tourism_agency_backend.services;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.tourism_agency_backend.models.TouristService;
import com.springboot.tourism_agency_backend.repositories.TouristServiceRepository;

@Service

public class TouristServiceServ {
    @Autowired
    private TouristServiceRepository touristServiceRepo;

    @Autowired
    private TourPackageService tourPackageService;

    @Transactional
    public List<TouristService> getTouristServices() {
        return this.touristServiceRepo.findAllByOrderById();
    }

    @Transactional
    public TouristService getTouristServiceById(int touristServiceId) {
        return this.touristServiceRepo.findById(touristServiceId).get();
    }

    @Transactional
    public TouristService saveOrUpdate(TouristService touristService, MultipartFile image, String action) throws IOException {
        switch (action) {
            case "add": {
                if(this.touristServiceRepo.findTouristServiceByName(touristService.getName()) == null) {
                    touristService.setImageData(image.getBytes());
                    touristService.setImageName(image.getOriginalFilename());
                    touristService.setImageType(image.getContentType());

                    return this.touristServiceRepo.save(touristService);
                }

                break;   
            }
        
            case "update": {
                TouristService currentTouristService = this.touristServiceRepo.findById(touristService.getId()).get();

                if(this.touristServiceRepo.findTouristServiceByNameExcludingCurrentOne(
                    touristService.getId(), touristService.getName()) == null) {

                    currentTouristService.setDate(touristService.getDate());
                    currentTouristService.setDescription(touristService.getDescription());
                    currentTouristService.setDestination(touristService.getDestination());
                    currentTouristService.setName(touristService.getName());
                    currentTouristService.setPrice(touristService.getPrice());
                    
                    if(image != null && !image.isEmpty()) {
                        currentTouristService.setImageData(image.getBytes());
                        currentTouristService.setImageName(image.getOriginalFilename());
                        currentTouristService.setImageType(image.getContentType());
                    }

                    this.touristServiceRepo.save(currentTouristService);
                    this.tourPackageService.updatePrice(currentTouristService);

                    return currentTouristService;
                }

                break;
            }
        }

        return null;
    }

    public TouristService deleteTouristService(int touristServiceId) {
        try {
            TouristService touristService = this.touristServiceRepo.findById(touristServiceId).get();
            this.touristServiceRepo.deleteById(touristServiceId);

            return touristService;
        }
        catch (Exception e) {
            return null;
        }
    }
}
