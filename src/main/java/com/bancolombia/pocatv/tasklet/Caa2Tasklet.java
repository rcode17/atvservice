package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrcaaService;

@Component
public class Caa2Tasklet implements Tasklet {
	private final AtvrcaaService atvrcaaService;

    public Caa2Tasklet(AtvrcaaService atvrcaaService) {
        this.atvrcaaService = atvrcaaService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
    	atvrcaaService.procesarDatosAnuales(anio);
        return RepeatStatus.FINISHED;
    }
}
