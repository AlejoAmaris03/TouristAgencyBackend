package com.springboot.tourism_agency_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.tourism_agency_backend.models.JobTitle;

@Repository

public interface JobTitleRepository extends JpaRepository<JobTitle, Integer>{
    public List<JobTitle> findAllByOrderById();
    public JobTitle findJobTitleByName(String name);

    @Query(
    """
        SELECT j
        FROM JobTitle j
        WHERE (j.id != ?1) AND (j.name = ?2)
    """)
    public JobTitle findJobTitleByNameExcludingCurrentOne(int jobTitleId, String name);
}
