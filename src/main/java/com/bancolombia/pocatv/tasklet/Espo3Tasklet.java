package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.Atvffsal1Service;

@Component
public class Espo3Tasklet implements Tasklet {
	private final Atvffsal1Service atvffsal1Service;

    public Espo3Tasklet(Atvffsal1Service atvffsal1Service) {
        this.atvffsal1Service = atvffsal1Service;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvffsal1Service.actualizarDocumento();
        return RepeatStatus.FINISHED;
    }
}
