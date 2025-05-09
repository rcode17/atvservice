package com.bancolombia.pocatv.batch.config;


import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobFactoryAtvrcad2  extends QuartzJobBean{
	

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job atvrcad2;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
                   
            jobLauncher.run(atvrcad2, new JobParametersBuilder()
            	    .addLong("time", System.currentTimeMillis()) // Añadir un parámetro único
            	    .addString("uid", UUID.randomUUID().toString())
            	    .toJobParameters());
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

}
