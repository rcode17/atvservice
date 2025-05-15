package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrcargueService;

@Component
public class CarsalTasklet implements Tasklet {
	private final AtvrcargueService atvrcargueService;

    public CarsalTasklet(AtvrcargueService atvrcargueService) {
        this.atvrcargueService = atvrcargueService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvrcargueService.procesarDatos();
        return RepeatStatus.FINISHED;
    }
}
