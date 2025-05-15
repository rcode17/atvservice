package com.bancolombia.pocatv.batch.config;

import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobFactoryAtvoespjde extends QuartzJobBean {
	
	 @Autowired
	    private JobLauncher jobLauncheratvoespjde;

	    @Autowired
	    private Job atvoespjde;

	    @Override
	    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	        try {
	                   
	        	jobLauncheratvoespjde.run(atvoespjde, new JobParametersBuilder()
	            	    .addLong("time", System.currentTimeMillis()) // Añadir un parámetro único
	            	    .addString("uid", UUID.randomUUID().toString())
	            	    .toJobParameters());
	        } catch (Exception e) {
	            throw new JobExecutionException(e);
	        }
	    }


}
