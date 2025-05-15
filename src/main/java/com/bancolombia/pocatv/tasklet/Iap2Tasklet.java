package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvriapService;

@Component
public class Iap2Tasklet implements Tasklet {
	private final AtvriapService atvriapService;

	public Iap2Tasklet(AtvriapService atvriapService) {
		this.atvriapService = atvriapService;
	}
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
    	atvriapService.generarArchivoCalidadInformacion(anio);
        return RepeatStatus.FINISHED;
    }
}
