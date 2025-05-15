package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffoasService;

@Component
public class Oas2Tasklet implements Tasklet {
	private final AtvffoasService atvffoasService;

    public Oas2Tasklet(AtvffoasService atvffoasService) {
        this.atvffoasService = atvffoasService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        int mes = now.getMonthValue();
    	atvffoasService.procesarActualizacion(mes,anio);
        return RepeatStatus.FINISHED;
    }
}
