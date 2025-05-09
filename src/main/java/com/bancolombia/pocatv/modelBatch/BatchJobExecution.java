package com.bancolombia.pocatv.modelBatch;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
public class BatchJobExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobExecutionId;

    @ManyToOne
    @JoinColumn(name = "JOB_INSTANCE_ID")
    private BatchJobInstance jobInstance;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    // Getters y Setters
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BatchJobInstance getJobInstance() {
        return jobInstance;
    }

    public void setJobInstance(BatchJobInstance jobInstance) {
        this.jobInstance = jobInstance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
