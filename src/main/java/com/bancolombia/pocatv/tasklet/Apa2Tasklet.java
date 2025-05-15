package com.bancolombia.pocatv.tasklet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.Atvrapa2Service;

@Component
public class Apa2Tasklet implements Tasklet {
	private final Atvrapa2Service atvrapa2Service;

    public Apa2Tasklet(Atvrapa2Service atvrapa2Service) {
        this.atvrapa2Service = atvrapa2Service;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    	LocalDate now = LocalDate.now();
        int anio = now.getYear();
        String fechaString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	atvrapa2Service.procesarArqueosAnormales(anio, fechaString);
        return RepeatStatus.FINISHED;
    }
}
