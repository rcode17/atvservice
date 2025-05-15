package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrcascaService;

@Component
public class CarscaTasklet implements Tasklet {
	private final AtvrcascaService atvrcascaService;

    public CarscaTasklet(AtvrcascaService atvrcascaService) {
        this.atvrcascaService = atvrcascaService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvrcascaService.procesarCargaInformacion();
        return RepeatStatus.FINISHED;
    }
}