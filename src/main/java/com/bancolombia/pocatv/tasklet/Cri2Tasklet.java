package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.Atvrcri2Service;

@Component
public class Cri2Tasklet implements Tasklet {
	private final Atvrcri2Service atvrcri2Service;

    public Cri2Tasklet(Atvrcri2Service atvrcri2Service) {
        this.atvrcri2Service = atvrcri2Service;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        int mes = now.getMonthValue();
    	atvrcri2Service.generarArchivoRangosInconsistentes(mes, anio);
        return RepeatStatus.FINISHED;
    }
}
