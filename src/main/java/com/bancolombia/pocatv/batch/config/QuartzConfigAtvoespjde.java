package com.bancolombia.pocatv.batch.config;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;


@Configuration
public class QuartzConfigAtvoespjde {
	
	 @Bean(name = "jobDetailAtvoespjde")
	    public JobDetailFactoryBean jobDetailAtvoespjde() {
	        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
	        factoryBean.setJobClass(QuartzJobFactoryAtvoespjde.class);
	        factoryBean.setDescription("Invocar el servicio de trabajo de ejemplo...");
	        factoryBean.setDurability(true);
	        return factoryBean;
	    }

	    @Bean(name = "triggerAtvoespjde")
	    public CronTriggerFactoryBean triggerAtvrcad2(@Qualifier("jobDetailAtvoespjde") JobDetail jobDetailAtvoespjde) {
	        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
	        factoryBean.setJobDetail(jobDetailAtvoespjde);
	        factoryBean.setCronExpression("0 0 2 * * ?"); // Ejecuta solo una vez a las 2:00 AM cada día
	        return factoryBean;
	    }

}
