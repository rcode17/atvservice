package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrcaracaService;

@Component
public class CaracaTasklet implements Tasklet {
	private final AtvrcaracaService atvrcaracaService;

	public CaracaTasklet(AtvrcaracaService atvrcaracaService) {
		this.atvrcaracaService = atvrcaracaService;
	}
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvrcaracaService.procesarArqueos();
        return RepeatStatus.FINISHED;
    }
}
