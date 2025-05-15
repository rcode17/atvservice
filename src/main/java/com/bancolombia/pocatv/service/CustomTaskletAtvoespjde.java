package com.bancolombia.pocatv.service;

import java.time.LocalDate;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.dto.ConciliacionRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomTaskletAtvoespjde implements Tasklet {
	
	@Autowired
	private AtvrcarajdService atvrcarajdService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		// Guardar la fecha actual en una variable
		LocalDate fechaActual = LocalDate.now();

		int ano = fechaActual.getYear();
		int mes = fechaActual.getMonthValue();
		int dia = fechaActual.getDayOfMonth();
	
		log.info("Ejecutando proceso Atvoespjde ");
		atvrcarajdService.procesarAtvrcarajd();
		
		log.info("Termino Ejecución Atvoespjde ");
		
		return RepeatStatus.FINISHED;
	}

}
