package com.springboot.tourism_agency_backend.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.tourism_agency_backend.models.TouristService;
import com.springboot.tourism_agency_backend.services.TouristServiceServ;

@CrossOrigin
@RestController
@RequestMapping("/touristServices")

public class TouristServiceController {
    @Autowired
    private TouristServiceServ touristServiceServ;

    @GetMapping
    public ResponseEntity<List<TouristService>> getTouristServices() {
        return ResponseEntity.ok(this.touristServiceServ.getTouristServices());
    }

    @GetMapping("/find/{touristServiceId}")
    public ResponseEntity<TouristService> getTouristServiceById(@PathVariable int touristServiceId) {
        return ResponseEntity.ok(this.touristServiceServ.getTouristServiceById(touristServiceId));
    }

    @GetMapping("/image/{touristServiceId}")
    public ResponseEntity<byte[]> getTouristServiceImageById(@PathVariable int touristServiceId) {
        TouristService touristService = this.touristServiceServ.getTouristServiceById(touristServiceId);

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(touristService.getImageType()))
            .body(touristService.getImageData());
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<TouristService> saveOrUpdate(@RequestPart TouristService touristService,
        @RequestPart MultipartFile image, String action) throws IOException {
        return ResponseEntity.ok(this.touristServiceServ.saveOrUpdate(touristService, image, action));
    }

    @DeleteMapping("/delete/{touristServiceId}")
    public ResponseEntity<TouristService> deleteTouristService(@PathVariable int touristServiceId) {
        return ResponseEntity.ok(this.touristServiceServ.deleteTouristService(touristServiceId));
    }
}
