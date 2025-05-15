package com.bancolombia.pocatv.repositoryBatch;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.modelBatch.BatchJobExecution;

@Repository
public interface BatchJobExecutionRepository extends CrudRepository<BatchJobExecution, Long> {

    @Query("SELECT COUNT(e) FROM BatchJobExecution e WHERE e.jobInstance.jobName = :jobName AND e.startTime >= :startTime AND e.startTime < :endTime")
    long countJobExecutionsForToday(String jobName, Date startTime, Date endTime);
    
    List<BatchJobExecution> findByJobInstanceJobName(String jobName);
    List<BatchJobExecution> findTop30ByOrderByStartTimeDesc();
}