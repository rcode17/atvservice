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
import com.bancolombia.pocatv.service.CustomTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfigAtvrcad2 {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomTasklet customTasklet;

    public BatchConfigAtvrcad2(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomTasklet customTasklet) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.customTasklet = customTasklet;
    }

    @Bean
    public Job atvrcad2() {
        return new JobBuilder("atvrcad2", jobRepository)
                .start(atvrcad2Step1())
                .build();
    }

    @Bean
    public Step atvrcad2Step1() {
        return new StepBuilder("atvrcad2Step1", jobRepository)
                .tasklet(customTasklet, transactionManager)
                .build();
    }
}