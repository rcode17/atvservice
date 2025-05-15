package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffCaeService;

@Component
public class Cae2Tasklet implements Tasklet {
	private final AtvffCaeService atvffCaeService;

    public Cae2Tasklet(AtvffCaeService atvffCaeService) {
        this.atvffCaeService = atvffCaeService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        int mes = now.getMonthValue();
    	atvffCaeService.generarReporteCumplimiento(mes, anio);
        return RepeatStatus.FINISHED;
    }
}
