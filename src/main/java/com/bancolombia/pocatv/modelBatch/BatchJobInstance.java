package com.bancolombia.pocatv.modelBatch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchJobInstance {

    @Id
    @Column(name = "JOB_INSTANCE_ID")
    private Long id;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_KEY")
    private String jobKey;
}
