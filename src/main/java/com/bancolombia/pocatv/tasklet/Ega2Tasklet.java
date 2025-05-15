package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.Atvrega2Service;

@Component
public class Ega2Tasklet implements Tasklet {
	private final Atvrega2Service atvrega2Service;

    public Ega2Tasklet(Atvrega2Service atvrega2Service) {
        this.atvrega2Service = atvrega2Service;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	atvrega2Service.generarReporteArqueos(null);
        return RepeatStatus.FINISHED;
    }
}
