package com.bancolombia.pocatv.service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.dto.ConciliacionRequest;

@Slf4j
@Component
public class CustomTasklet implements Tasklet {

	@Autowired
	private AtvffcadService atvffcadService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		// Guardar la fecha actual en una variable
		LocalDate fechaActual = LocalDate.now();

		int ano = fechaActual.getYear();
		int mes = fechaActual.getMonthValue();
		int dia = fechaActual.getDayOfMonth();

		// Lógica de procesamiento

		System.out.println("Ejecutando proceso para la fecha: ");
		log.info("Ejecutando proceso para la fecha: ");
		ConciliacionRequest conciliacionRequest = new ConciliacionRequest();
		conciliacionRequest.setAno(ano);
		conciliacionRequest.setMes(mes);
		conciliacionRequest.setDia(dia);
		atvffcadService.generarConciliacion(conciliacionRequest);
		log.info("Termino Ejecución ");
		System.out.println("Termino Ejecución ");
		return RepeatStatus.FINISHED;
	}
}