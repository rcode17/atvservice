package com.bancolombia.pocatv.repositoryBatch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.modelBatch.StepExecution;

@Repository
public interface StepExecutionRepository extends JpaRepository<StepExecution, Long> {
    List<StepExecution> findByJobExecutionId(Long jobExecutionId);
}