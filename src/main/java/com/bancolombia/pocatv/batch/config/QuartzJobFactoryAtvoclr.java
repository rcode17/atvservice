package com.bancolombia.pocatv.batch.config;

import java.util.UUID;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class QuartzJobFactoryAtvoclr extends QuartzJobBean {
	
	 @Autowired
	    private JobLauncher jobLauncheratvoclr;

	    @Autowired
	    private Job atvoclr;

	    @Override
	    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	        try {
	                   
	        	jobLauncheratvoclr.run(atvoclr, new JobParametersBuilder()
	            	    .addLong("time", System.currentTimeMillis())
	            	    .addString("uid", UUID.randomUUID().toString())// Añadir un parámetro único
	            	    .toJobParameters());
	        } catch (Exception e) {
	            throw new JobExecutionException(e);
	        }
	    }


}
