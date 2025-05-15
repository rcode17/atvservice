package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvfftitvaService;

@Component
public class CaratsTasklet implements Tasklet {
	private final AtvfftitvaService atvfftitvaService;

	public CaratsTasklet(AtvfftitvaService atvfftitvaService) {
		this.atvfftitvaService = atvfftitvaService;
	}

	@Override
	public RepeatStatus execute(StepContribution c, ChunkContext ctx) {
		atvfftitvaService.procesarInformacionArqueo();
		return RepeatStatus.FINISHED;
	}
}
