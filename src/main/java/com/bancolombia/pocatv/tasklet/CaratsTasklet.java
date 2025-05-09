package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CaratsTasklet implements Tasklet {
	/*private final CaratsService service;

	public CaratsTasklet(CaratsService service) {
		this.service = service;
	}*/

	@Override
	public RepeatStatus execute(StepContribution c, ChunkContext ctx) {
		//service.procesarCarats();
		return RepeatStatus.FINISHED;
	}
}
