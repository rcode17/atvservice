package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.repository.AtvffcrdRepository;

@Component
public class ClearCrdTasklet implements Tasklet{
	
	@Autowired
	private AtvffcrdRepository atvffcrdRepository;

	@Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		atvffcrdRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }

}
