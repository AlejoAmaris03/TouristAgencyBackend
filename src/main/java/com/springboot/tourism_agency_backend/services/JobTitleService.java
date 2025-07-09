package com.springboot.tourism_agency_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.tourism_agency_backend.models.JobTitle;
import com.springboot.tourism_agency_backend.repositories.JobTitleRepository;

@Service

public class JobTitleService {
    @Autowired
    private JobTitleRepository jobTitleRepo;

    public List<JobTitle> getJobTitles() {
        return this.jobTitleRepo.findAllByOrderById();
    }

    public JobTitle getJobTitleById(int jobTitleId) {
        return this.jobTitleRepo.findById(jobTitleId).get();
    }

    public JobTitle saveOrUpdate(JobTitle jobTitle, String action) {
        switch (action) {
            case "add": {
                if(this.jobTitleRepo.findJobTitleByName(jobTitle.getName()) == null)
                    return this.jobTitleRepo.save(jobTitle);

                break;
            }
        
            case "update": {
                if(this.jobTitleRepo.findJobTitleByNameExcludingCurrentOne(jobTitle.getId(), jobTitle.getName()) == null)
                    return this.jobTitleRepo.save(jobTitle);

                break;
            }
        }

        return null;
    }

    public JobTitle deleteJobTitle(int jobTitleId) {
        try {
            JobTitle jobTitle = this.jobTitleRepo.findById(jobTitleId).get();
            this.jobTitleRepo.deleteById(jobTitleId);

            return jobTitle;
        } 
        catch (Exception e) {
            return null;
        }
    }
}
