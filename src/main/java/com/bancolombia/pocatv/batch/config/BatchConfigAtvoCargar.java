package com.bancolombia.pocatv.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.bancolombia.pocatv.model.Atvfffil;
import com.bancolombia.pocatv.model.Atvffsai1;
import com.bancolombia.pocatv.model.Atvffsai2;
import com.bancolombia.pocatv.model.Atvfftem;
import com.bancolombia.pocatv.tasklet.Apa2Tasklet;
import com.bancolombia.pocatv.tasklet.AtvrcarajdTasklet;
import com.bancolombia.pocatv.tasklet.Caa2Tasklet;
import com.bancolombia.pocatv.tasklet.Cae2Tasklet;
import com.bancolombia.pocatv.tasklet.CaracaTasklet;
import com.bancolombia.pocatv.tasklet.CarameTasklet;
import com.bancolombia.pocatv.tasklet.CaratsTasklet;
import com.bancolombia.pocatv.tasklet.CarsalTasklet;
import com.bancolombia.pocatv.tasklet.CarscaTasklet;
import com.bancolombia.pocatv.tasklet.CarsjdTasklet;
import com.bancolombia.pocatv.tasklet.CarsmeTasklet;
import com.bancolombia.pocatv.tasklet.Cep2Tasklet;
import com.bancolombia.pocatv.tasklet.ClearCrdTasklet;
import com.bancolombia.pocatv.tasklet.Cps2Tasklet;
import com.bancolombia.pocatv.tasklet.Crd0Tasklet;
import com.bancolombia.pocatv.tasklet.Cri2Tasklet;
import com.bancolombia.pocatv.tasklet.Ega2Tasklet;
import com.bancolombia.pocatv.tasklet.Espo3Tasklet;
import com.bancolombia.pocatv.tasklet.Frein1Tasklet;
import com.bancolombia.pocatv.tasklet.Iaa2Tasklet;
import com.bancolombia.pocatv.tasklet.Iap2Tasklet;
import com.bancolombia.pocatv.tasklet.Oas2Tasklet;
import com.bancolombia.pocatv.tasklet.SqlarrTasklet;
import com.bancolombia.pocatv.tasklet.ValTasklet;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfigAtvoCargar {
	private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    // Constructor para inyectar las dependencias necesarias
    public BatchConfigAtvoCargar(JobRepository jobRepository, PlatformTransactionManager transactionManager, EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
    }
    
    /**
     * Configuración del Job que ejecuta Steps.
     */
    @Bean
    public Job atvoCargarJob(
            Step processSa1Step,
            Step processSa2Step,
            Step atvrcarajdStep,
            Step caratsStep,
            Step carameStep,
            Step caracaStep,
            Step sqlarrStep,
            Step carsalStep,
            Step carsjdStep,
            Step carsmeStep,
            Step carscaStep,
            Step valStep,
            Step cae2Step,
            Step oas2Step,
            Step cep2Step,
            Step ega2Step,
            Step iaa2Step,
            Step caa2Step,
            Step cps2Step,
            Step iap2Step,
            Step cri2Step,
            Step clearCrdStep,
            Step crd0Step,
            Step apa2Step,
            Step frein1Step,
            Step espo3Step) {

        return new JobBuilder("atvoCargarJob", jobRepository)
                .start(processSa1Step)
                .next(processSa2Step)
                .next(atvrcarajdStep)
                .next(caratsStep)
                .next(carameStep)
                .next(caracaStep)
                .next(sqlarrStep)
                .next(carsalStep)
                .next(carsjdStep)
                .next(carsmeStep)
                .next(carscaStep)
                .next(valStep)
                .next(cae2Step)
                .next(oas2Step)
                .next(cep2Step)
                .next(ega2Step)
                .next(iaa2Step)
                .next(caa2Step)
                .next(cps2Step)
                .next(iap2Step)
                .next(cri2Step)
                .next(clearCrdStep)
                .next(crd0Step)
                .next(apa2Step)
                .next(frein1Step)
                .next(espo3Step)
                .build();
    }


    /**
     * Step para procesar ATVFFSAI1 y escribir en ATVFFFIL. - ATVRCARGUE
     */
    @Bean
    public Step processSa1Step(
            ItemReader<Atvffsai1> readerSa1,
            ItemProcessor<Atvffsai1, Atvfffil> processorSa1,
            ItemWriter<Atvfffil> writerSa1) {
        return new StepBuilder("processSa1Step", jobRepository)
                .<Atvffsai1, Atvfffil>chunk(10, transactionManager) // Chunk size de 10
                .reader(readerSa1)
                .processor(processorSa1)
                .writer(writerSa1)
                .build();
    }

    /**
     * Step para procesar ATVFFSAI2 y escribir en ATVFFTEM. - ATVRCARGUE 
     */
    @Bean
    public Step processSa2Step(
            ItemReader<Atvffsai2> readerSa2,
            ItemProcessor<Atvffsai2, Atvfftem> processorSa2,
            ItemWriter<Atvfftem> writerSa2) {
        return new StepBuilder("processSa2Step", jobRepository)
                .<Atvffsai2, Atvfftem>chunk(10, transactionManager) // Chunk size de 10
                .reader(readerSa2)
                .processor(processorSa2)
                .writer(writerSa2)
                .build();
    }
    
    
    @Bean public Step atvrcarajdStep(AtvrcarajdTasklet t) { return step("atvrcarajdStep", t); }
    @Bean public Step caratsStep(CaratsTasklet t) { return step("caratsStep", t); }
    @Bean public Step carameStep(CarameTasklet t) { return step("carameStep", t); }
    @Bean public Step caracaStep(CaracaTasklet t) { return step("caracaStep", t); }
    @Bean public Step sqlarrStep(SqlarrTasklet t) { return step("sqlarrStep", t); }
    @Bean public Step carsalStep(CarsalTasklet t) { return step("carsalStep", t); }
    @Bean public Step carsjdStep(CarsjdTasklet t) { return step("carsjdStep", t); }
    @Bean public Step carsmeStep(CarsmeTasklet t) { return step("carsmeStep", t); }
    @Bean public Step carscaStep(CarscaTasklet t) { return step("carscaStep", t); }
    @Bean public Step valStep(ValTasklet t) { return step("valStep", t); }
    @Bean public Step cae2Step(Cae2Tasklet t) { return step("cae2Step", t); }
    @Bean public Step oas2Step(Oas2Tasklet t) { return step("oas2Step", t); }
    @Bean public Step cep2Step(Cep2Tasklet t) { return step("cep2Step", t); }
    @Bean public Step ega2Step(Ega2Tasklet t) { return step("ega2Step", t); }
    @Bean public Step iaa2Step(Iaa2Tasklet t) { return step("iaa2Step", t); }
    @Bean public Step caa2Step(Caa2Tasklet t) { return step("caa2Step", t); }
    @Bean public Step cps2Step(Cps2Tasklet t) { return step("cps2Step", t); }
    @Bean public Step iap2Step(Iap2Tasklet t) { return step("iap2Step", t); }
    @Bean public Step cri2Step(Cri2Tasklet t) { return step("cri2Step", t); }
    @Bean public Step clearCrdStep(ClearCrdTasklet t) { return step("clearCrdStep", t); }
    @Bean public Step crd0Step(Crd0Tasklet t) { return step("crd0Step", t); }
    @Bean public Step apa2Step(Apa2Tasklet t) { return step("apa2Step", t); }
    @Bean public Step frein1Step(Frein1Tasklet t) { return step("frein1Step", t); }
    @Bean public Step espo3Step(Espo3Tasklet t) { return step("espo3Step", t); }

    private Step step(String name, org.springframework.batch.core.step.tasklet.Tasklet tasklet) {
        return new StepBuilder(name, jobRepository).tasklet(tasklet, transactionManager).build();
    }


}
