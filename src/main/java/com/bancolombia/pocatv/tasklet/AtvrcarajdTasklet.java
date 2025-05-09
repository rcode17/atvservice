package com.bancolombia.pocatv.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.bancolombia.pocatv.service.AtvrcarajdService;

@Component
public class AtvrcarajdTasklet implements Tasklet {

    private final AtvrcarajdService atvrcarajdService;

    public AtvrcarajdTasklet(AtvrcarajdService atvrcarajdService) {
        this.atvrcarajdService = atvrcarajdService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        atvrcarajdService.procesarAtvrcarajd();
        return RepeatStatus.FINISHED;
    }
}
