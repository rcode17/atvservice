package com.bancolombia.pocatv.batch.config;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfigBatchAtvoCargar {

    @Bean(name = "jobDetailAtvoCargar")
    public JobDetailFactoryBean jobDetailAtvoCargar() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJobFactoryBatchAtvoCargar.class);
        factoryBean.setDescription("Invocar el servicio de trabajo de ejemplo...");
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean(name = "triggerAtvoCargar")
    public CronTriggerFactoryBean triggerAtvoCargar(@Qualifier("jobDetailAtvoCargar") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        //factoryBean.setCronExpression("10 * * * * ?");
        factoryBean.setCronExpression("0 0 3 * * ?");
        return factoryBean;
    }
}
