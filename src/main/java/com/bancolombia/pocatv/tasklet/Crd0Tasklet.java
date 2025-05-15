package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffarqService;

@Component
public class Crd0Tasklet implements Tasklet {
	private final AtvffarqService atvffarqService;

    public Crd0Tasklet(AtvffarqService atvffarqService) {
        this.atvffarqService = atvffarqService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvffarqService.procesarArqueosDescuadrados();
        return RepeatStatus.FINISHED;
    }
}