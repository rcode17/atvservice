package com.bancolombia.pocatv.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.modelBatch.BatchJobExecution;
import com.bancolombia.pocatv.modelBatch.StepExecution;
import com.bancolombia.pocatv.repositoryBatch.BatchJobExecutionRepository;
import com.bancolombia.pocatv.repositoryBatch.StepExecutionRepository;

@Service
public class BatchMonitoringService {

    private final BatchJobExecutionRepository jobExecutionRepo;
    private final StepExecutionRepository stepExecutionRepo;

    public BatchMonitoringService(BatchJobExecutionRepository jobExecutionRepo, StepExecutionRepository stepExecutionRepo) {
        this.jobExecutionRepo = jobExecutionRepo;
        this.stepExecutionRepo = stepExecutionRepo;
    }

    public List<BatchJobExecution> getAllExecutions(String jobName) {
        return jobExecutionRepo.findByJobInstanceJobName(jobName);
    }

    public List<StepExecution> getStepsForExecution(Long jobExecutionId) {
        return stepExecutionRepo.findByJobExecutionId(jobExecutionId);
    }
    
    public List<BatchJobExecution> getAllExecutionsOrdered() {
        return jobExecutionRepo.findTop30ByOrderByStartTimeDesc();
    }
}
