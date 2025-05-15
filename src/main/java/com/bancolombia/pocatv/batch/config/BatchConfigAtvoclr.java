package com.bancolombia.pocatv.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.bancolombia.pocatv.service.AtvoclrCustomTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfigAtvoclr {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final AtvoclrCustomTasklet atvoclrCustomTasklet;

    public BatchConfigAtvoclr(JobRepository jobRepository, 
                              PlatformTransactionManager transactionManager, 
                              AtvoclrCustomTasklet atvoclrCustomTasklet) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.atvoclrCustomTasklet = atvoclrCustomTasklet;
    }

    @Bean
    public Job atvoclr() {
        return new JobBuilder("atvoclr", jobRepository)
                .start(limpiarStep1())
                .build();
    }

    @Bean
    public Step limpiarStep1() {
        return new StepBuilder("limpiarStep1", jobRepository)
                .tasklet(atvoclrCustomTasklet, transactionManager)
                .build();
    }
}