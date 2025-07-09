package com.springboot.tourism_agency_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.tourism_agency_backend.models.TourPackage;
import com.springboot.tourism_agency_backend.services.TourPackageService;

@CrossOrigin
@RestController
@RequestMapping("/tourPackages")

public class TourPackageController {
    @Autowired
    private TourPackageService tourPackageService;

    @GetMapping
    public ResponseEntity<List<TourPackage>> getTourPakages() {
        return ResponseEntity.ok(this.tourPackageService.getTourPackages());
    }

    @PostMapping("/saveTourPackage")
    public ResponseEntity<TourPackage> saveTourPackage(@RequestBody TourPackage tourPackage) {
        return ResponseEntity.ok(this.tourPackageService.saveTourPackage(tourPackage));
    }

    @PutMapping("/updateName")
    public ResponseEntity<TourPackage> updateTourPackageName(@RequestPart String tourPackageId, 
        @RequestPart String name) {
        return ResponseEntity.ok(this.tourPackageService.updateTourPackageName(tourPackageId, name));
    }

    @PutMapping("/addServiceToPackage")
    public ResponseEntity<TourPackage> addServiceToPackage(@RequestBody TourPackage tourPackage) {
        return ResponseEntity.ok(this.tourPackageService.addServiceToPackage(tourPackage));
    }

    @PutMapping("/removeServiceFromPackage")
    public ResponseEntity<TourPackage> removeServiceFromPackage(@RequestPart String tourPackageId, 
        @RequestPart String touristServiceId) {
        return ResponseEntity.ok(this.tourPackageService.removeServiceFromPackage(tourPackageId, touristServiceId));
    }

    @DeleteMapping("/deletePackage/{tourPackageId}")
    public ResponseEntity<TourPackage> deletePackage(@PathVariable int tourPackageId) {
        return ResponseEntity.ok(this.tourPackageService.deletePackage(tourPackageId));
    }
}
