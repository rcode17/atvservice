package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffsalService;

@Component
public class CarsjdTasklet implements Tasklet {
	private final AtvffsalService atvffsalService;

    public CarsjdTasklet(AtvffsalService atvffsalService) {
        this.atvffsalService = atvffsalService;
    }
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvffsalService.updateSaofcoWithSaofic();
        return RepeatStatus.FINISHED;
    }
}
