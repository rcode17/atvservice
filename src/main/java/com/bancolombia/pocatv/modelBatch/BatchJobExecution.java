package com.bancolombia.pocatv.modelBatch;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchJobExecution {

    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "JOB_INSTANCE_ID")
    private BatchJobInstance jobInstance;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EXIT_CODE")
    private String exitCode;

    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
}
