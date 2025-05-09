package com.bancolombia.pocatv.modelBatch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
public class BatchJobInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobInstanceId;

    @Column(name = "JOB_NAME")
    private String jobName;

    // Getters y Setters
    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
