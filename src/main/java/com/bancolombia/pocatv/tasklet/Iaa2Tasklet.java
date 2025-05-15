package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffiaaService;

@Component
public class Iaa2Tasklet implements Tasklet {
	private final AtvffiaaService atvffiaaService;

    public Iaa2Tasklet(AtvffiaaService atvffiaaService) {
        this.atvffiaaService = atvffiaaService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        atvffiaaService.generarEstadisticas(anio);
        return RepeatStatus.FINISHED;
    }
}
