package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvffcepService;

@Component
public class Cep2Tasklet implements Tasklet {
	private final AtvffcepService atvffcepService;

    public Cep2Tasklet(AtvffcepService atvffcepService) {
        this.atvffcepService = atvffcepService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        int mes = now.getMonthValue();
    	atvffcepService.procesarConsultaEspecifica(mes, anio);
        return RepeatStatus.FINISHED;
    }
}
