package com.bancolombia.pocatv.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.pocatv.modelBatch.BatchJobExecution;
import com.bancolombia.pocatv.modelBatch.StepExecution;
import com.bancolombia.pocatv.service.BatchMonitoringService;

@RestController
@RequestMapping("/api/batch-monitor")
public class BatchMonitoringController {

    private final BatchMonitoringService monitoringService;

    public BatchMonitoringController(BatchMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/executions/{jobName}")
    public List<BatchJobExecution> getExecutions(@PathVariable String jobName) {
        return monitoringService.getAllExecutions(jobName);
    }

    @GetMapping("/steps/{jobExecutionId}")
    public List<StepExecution> getStepExecutions(@PathVariable Long jobExecutionId) {
        return monitoringService.getStepsForExecution(jobExecutionId);
    }
    
    @GetMapping("/executions")
    public List<BatchJobExecution> getExecutionsAll() {
        return monitoringService.getAllExecutionsOrdered();
    }
}

