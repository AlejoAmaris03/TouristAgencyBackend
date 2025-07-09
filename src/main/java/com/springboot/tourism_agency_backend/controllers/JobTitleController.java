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
import com.springboot.tourism_agency_backend.models.JobTitle;
import com.springboot.tourism_agency_backend.services.JobTitleService;

@CrossOrigin
@RestController
@RequestMapping("/jobTitles")

public class JobTitleController {
    @Autowired
    private JobTitleService jobTitleService;

    @GetMapping
    public ResponseEntity<List<JobTitle>> getJobTitles() {
        return ResponseEntity.ok(this.jobTitleService.getJobTitles());
    }

    @GetMapping("/find/{jobTitleId}")
    public ResponseEntity<JobTitle> getJobTitleById(@PathVariable int jobTitleId) {
        return ResponseEntity.ok(this.jobTitleService.getJobTitleById(jobTitleId));
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<JobTitle> saveOrUpdate(@RequestPart JobTitle jobTitle, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.jobTitleService.saveOrUpdate(jobTitle, action));
    }

    @DeleteMapping("/delete/{jobTitleId}")
    public ResponseEntity<JobTitle> deleteJobTitle(@PathVariable int jobTitleId) {
        return ResponseEntity.ok(this.jobTitleService.deleteJobTitle(jobTitleId));
    }
}
