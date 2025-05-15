package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrvalService;

@Component
public class CarsmeTasklet implements Tasklet {
	private final AtvrvalService atvrvalService;

    public CarsmeTasklet(AtvrvalService atvrvalService) {
        this.atvrvalService = atvrvalService;
    }
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvrvalService.procesarDatosAtvffmesal();
        return RepeatStatus.FINISHED;
    }
}