package com.bancolombia.pocatv.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.bancolombia.pocatv.service.CustomTaskletAtvoespjde;
import com.bancolombia.pocatv.tasklet.Apa2Tasklet;
import com.bancolombia.pocatv.tasklet.Caa2Tasklet;
import com.bancolombia.pocatv.tasklet.Cae2Tasklet;
import com.bancolombia.pocatv.tasklet.Cep2Tasklet;
import com.bancolombia.pocatv.tasklet.ClearCrdTasklet;
import com.bancolombia.pocatv.tasklet.ClearTempAll;
import com.bancolombia.pocatv.tasklet.Cps2Tasklet;
import com.bancolombia.pocatv.tasklet.Crd0Tasklet;
import com.bancolombia.pocatv.tasklet.Cri2Tasklet;
import com.bancolombia.pocatv.tasklet.Ega2Tasklet;
import com.bancolombia.pocatv.tasklet.Frein1Tasklet;
import com.bancolombia.pocatv.tasklet.Iaa2Tasklet;
import com.bancolombia.pocatv.tasklet.Iap2Tasklet;
import com.bancolombia.pocatv.tasklet.Oas2Tasklet;
import com.bancolombia.pocatv.tasklet.SqlarrTasklet;
import com.bancolombia.pocatv.tasklet.ValTasklet;


@Configuration
@EnableBatchProcessing
public class BatchConfigAtvoespjde {
	
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final CustomTaskletAtvoespjde customTaskletAtvoespjde;
	private final SqlarrTasklet sqlarrTasklet;
	private final ValTasklet valTasklet;
	private final Cae2Tasklet cae2Tasklet;
	private final Oas2Tasklet oas2Tasklet;
	private final Cep2Tasklet cep2Tasklet;
	private final Ega2Tasklet ega2Tasklet;
	private final Iaa2Tasklet iaa2Tasklet;
	private final Caa2Tasklet caa2Tasklet;
	private final Cps2Tasklet cps2Tasklet;
	private final Iap2Tasklet iap2Tasklet;
	private final Cri2Tasklet cri2Tasklet;
	private final ClearCrdTasklet clearCrdTasklet;
	private final Crd0Tasklet crd0Tasklet;
	private final Apa2Tasklet apa2Tasklet;
	private final Frein1Tasklet frein1Tasklet;
	private final ClearTempAll clearTempAll;
	
	

	public BatchConfigAtvoespjde(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomTaskletAtvoespjde customTaskletAtvoespjde,
			SqlarrTasklet sqlarrTasklet, ValTasklet valTasklet,
			Cae2Tasklet cae2Tasklet, Oas2Tasklet oas2Tasklet,
			Cep2Tasklet cep2Tasklet, Ega2Tasklet ega2Tasklet,
			Iaa2Tasklet iaa2Tasklet,Caa2Tasklet caa2Tasklet,
			Cps2Tasklet cps2Tasklet, Iap2Tasklet iap2Tasklet,
			Cri2Tasklet cri2Tasklet,ClearCrdTasklet clearCrdTasklet,
			Crd0Tasklet crd0Tasklet, Apa2Tasklet apa2Tasklet,
			Frein1Tasklet frein1Tasklet, ClearTempAll clearTempAll) {
	        this.jobRepository = jobRepository;
	        this.transactionManager = transactionManager;
	        this.customTaskletAtvoespjde = customTaskletAtvoespjde;
	        this.sqlarrTasklet = sqlarrTasklet;
	        this.valTasklet = valTasklet;
	        this.cae2Tasklet = cae2Tasklet;
	        this.oas2Tasklet = oas2Tasklet;
	        this.cep2Tasklet = cep2Tasklet;
	        this.ega2Tasklet = ega2Tasklet;
	        this.iaa2Tasklet = iaa2Tasklet;
	        this.caa2Tasklet = caa2Tasklet;
	        this.cps2Tasklet = cps2Tasklet;
	        this.iap2Tasklet = iap2Tasklet;
	        this.cri2Tasklet = cri2Tasklet;
	        this.clearCrdTasklet = clearCrdTasklet;
	        this.crd0Tasklet = crd0Tasklet;
	        this.apa2Tasklet = apa2Tasklet;
	        this.frein1Tasklet = frein1Tasklet;
	        this.clearTempAll = clearTempAll;
	    }

	@Bean
	public Job atvoespjde() {
		return new JobBuilder("atvoespjde", jobRepository)
				.start(atvoespjdeStep1())
				.next(atvoespjdeStep2())
				.next(atvoespjdeStep3())
				.next(atvoespjdeStep4())
				.next(atvoespjdeStep5())
				.next(atvoespjdeStep6())
				.next(atvoespjdeStep7())
				.next(atvoespjdeStep8())
				.next(atvoespjdeStep9())
				.next(atvoespjdeStep10())
				.next(atvoespjdeStep11())
				.next(atvoespjdeStep12())
				.next(atvoespjdeStep13())
				.next(atvoespjdeStep14())
				.next(atvoespjdeStep15())
				.next(atvoespjdeStep16())
				.next(atvoespjdeStep17())
				.build();
	}

	@Bean
	public Step atvoespjdeStep1() {
		return new StepBuilder("atvoespjdeStep1", jobRepository).tasklet(customTaskletAtvoespjde, transactionManager).build();
	}
	

    @Bean
    public Step atvoespjdeStep2() {
        return new StepBuilder("atvoespjdeStep2", jobRepository)
                .tasklet(sqlarrTasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep3() {
        return new StepBuilder("atvoespjdeStep3", jobRepository)
                .tasklet(valTasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep4() {
        return new StepBuilder("atvoespjdeStep4", jobRepository)
                .tasklet(cae2Tasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep5() {
        return new StepBuilder("atvoespjdeStep5", jobRepository)
                .tasklet(oas2Tasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep6() {
        return new StepBuilder("atvoespjdeStep6", jobRepository)
                .tasklet(cep2Tasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep7() {
        return new StepBuilder("atvoespjdeStep6", jobRepository)
                .tasklet(ega2Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep8() {
        return new StepBuilder("atvoespjdeStep8", jobRepository)
                .tasklet(iaa2Tasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep9() {
        return new StepBuilder("atvoespjdeStep9", jobRepository)
                .tasklet(caa2Tasklet, transactionManager)
                .build();
    }
    
    @Bean
    public Step atvoespjdeStep10() {
        return new StepBuilder("atvoespjdeStep10", jobRepository)
                .tasklet(cps2Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep11() {
        return new StepBuilder("atvoespjdeStep11", jobRepository)
                .tasklet(iap2Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep12() {
        return new StepBuilder("atvoespjdeStep12", jobRepository)
                .tasklet(cri2Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep13() {
        return new StepBuilder("atvoespjdeStep13", jobRepository)
                .tasklet(clearCrdTasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep14() {
        return new StepBuilder("atvoespjdeStep14", jobRepository)
                .tasklet(crd0Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep15() {
        return new StepBuilder("atvoespjdeStep15", jobRepository)
                .tasklet(apa2Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep16() {
        return new StepBuilder("atvoespjdeStep16", jobRepository)
                .tasklet(frein1Tasklet, transactionManager)
                .build();
    }
    @Bean
    public Step atvoespjdeStep17() {
        return new StepBuilder("atvoespjdeStep17", jobRepository)
                .tasklet(clearTempAll, transactionManager)
                .build();
    }
    
	
	
	
	
}
