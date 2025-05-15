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
public class AtvoclrCustomTasklet implements Tasklet{
	
	@Autowired
	private AtvffarqService atvffarqService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		log.info("Ejecutando proceso Atvoclr ");
		atvffarqService.limpiarArchivosArqueo();
		log.info("Termino Ejecución Atvoclr ");
	
		return RepeatStatus.FINISHED;
	}
}
	
