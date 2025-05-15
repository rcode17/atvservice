package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.repository.AtvffTemf1Repository;
import com.bancolombia.pocatv.repository.AtvffcharqRepository;
import com.bancolombia.pocatv.repository.AtvffcrdRepository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;

@Component
public class ClearTempAll implements Tasklet {
	
	@Autowired
	private AtvffTemf1Repository atvffTemf1Repository;
	
	@Autowired
	private AtvfftemRepository atvfftemRepository;
	
	private AtvffcharqRepository atvffcharqRepository;

	@Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		atvffTemf1Repository.deleteAll();
		atvfftemRepository.deleteAll();
		atvffcharqRepository.deleteAll();
				
        return RepeatStatus.FINISHED;
    }

}
