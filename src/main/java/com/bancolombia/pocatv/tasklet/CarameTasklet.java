package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffmearqService;

@Component
public class CarameTasklet implements Tasklet{
	private final AtvffmearqService atvffmearqService;

	public CarameTasklet(AtvffmearqService atvffmearqService) {
		this.atvffmearqService = atvffmearqService;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		atvffmearqService.procesarArqueos();
		return RepeatStatus.FINISHED;
	}

}
