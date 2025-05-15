package com.bancolombia.pocatv.batch.config;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfigAtvrcad2 {

    @Bean(name = "jobDetailAtvrcad2")
    public JobDetailFactoryBean jobDetailAtvrcad2() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJobFactoryAtvrcad2.class);
        factoryBean.setDescription("Invocar el servicio de trabajo de ejemplo...");
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean(name = "triggerAtvrcad2")
    public CronTriggerFactoryBean triggerAtvrcad2(@Qualifier("jobDetailAtvrcad2") JobDetail jobDetailAtvrcad2) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetailAtvrcad2);
        factoryBean.setCronExpression("0 0 2 * * ?");
        //factoryBean.setCronExpression("0 * * * * ?");// Ejecuta solo una vez a las 2:00 AM cada día
        return factoryBean;
    }
}