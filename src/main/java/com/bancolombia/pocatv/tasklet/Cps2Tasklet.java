package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.Atvrcps2Service;

@Component
public class Cps2Tasklet implements Tasklet {
	private final Atvrcps2Service atvrcps2Service;

    public Cps2Tasklet(Atvrcps2Service atvrcps2Service) {
        this.atvrcps2Service = atvrcps2Service;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        int mes = now.getMonthValue();
    	atvrcps2Service.procesarActualizacion(mes, anio);
        return RepeatStatus.FINISHED;
    }
}
