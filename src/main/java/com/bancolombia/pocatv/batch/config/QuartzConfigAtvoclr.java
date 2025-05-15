package com.bancolombia.pocatv.batch.config;

import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfigAtvoclr {

    @Bean
    public JobDetailFactoryBean jobDetailAtvoclr() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJobFactoryAtvoclr.class);
        factoryBean.setDescription("Invocar el servicio de trabajo de ejemplo...");
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean triggerAtvoclr(JobDetail jobDetailAtvoclr) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetailAtvoclr);
        factoryBean.setCronExpression("0 0 2 * * ?"); // Ejecuta solo una vez a las 2:00 AM cada día
        return factoryBean;
    }
}