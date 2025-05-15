package com.bancolombia.pocatv.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.pocatv.repository.AtvffcaarqRepository;
import com.bancolombia.pocatv.repository.AtvffcasalRepository;
import com.bancolombia.pocatv.repository.AtvffcharqRepository;
import com.bancolombia.pocatv.repository.AtvffchsalRepository;
import com.bancolombia.pocatv.repository.AtvfffreinRepository;
import com.bancolombia.pocatv.repository.AtvffmearqRepository;
import com.bancolombia.pocatv.repository.AtvffmesalRepository;
import com.bancolombia.pocatv.repository.Atvffsai1Repository;
import com.bancolombia.pocatv.repository.Atvffsai2Repository;
import com.bancolombia.pocatv.repository.AtvfftemRepository;
import com.bancolombia.pocatv.repository.AtvfftitvaRepository;
import com.bancolombia.pocatv.repositoryBatch.BatchJobExecutionRepository;
import com.bancolombia.pocatv.service.MainBatchAtvoCargarService;

@Service
public class MainBatchAtvoCargarServiceImpl implements MainBatchAtvoCargarService {
	
	@Autowired
    private JobLauncher jobLauncher;
	
	@Autowired
    private Job atvoCargarJob;
	
	@Autowired
    private BatchJobExecutionRepository batchJobExecutionRepository;
	
	@Autowired
	private AtvfftemRepository atvfftemRepository;
	
	@Autowired
	private AtvfffreinRepository atvfffreinRepository;
	
	/*@Autowired
    private Atvfftem1Repository atvfftem1Repository;*/

    @Autowired
    private Atvffsai1Repository atvffsa1Repository;

    @Autowired
    private Atvffsai2Repository atvffsa2Repository;
    

    @Autowired
    private AtvffcharqRepository atvffcharqRepository;

    @Autowired
    private AtvffchsalRepository atvffchsRepository;

    @Autowired
    private AtvfftitvaRepository atvfftitvaRepository;

    @Autowired
    private AtvffmearqRepository atvffmeaqRepository;

    @Autowired
    private AtvffmesalRepository atvffmesalRepository;

    @Autowired
    private AtvffcaarqRepository atvffcaaraqRepository;

    @Autowired
    private AtvffcasalRepository atvffcasalRepository;

	@Override
	public void executeBatch() {
		try {
            // Validar que el proceso no se ejecute más de una vez al día
            /*if (isAlreadyExecutedToday("atvoCargarJob")) {
                sendUserMessage("El proceso ATV no puede ser ejecutado dos veces en un día. Por favor verifique que no haya sido ejecutado ya.");
                return;
            }*/

            // Limpieza de archivos
            clearFiles();

            // Ejecución del job de Spring Batch
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(atvoCargarJob, jobParameters);


            // Limpieza final
            finalClearFiles();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean isAlreadyExecutedToday(String jobName) {
        LocalDate today = LocalDate.now();
        Date startTime = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        long count = batchJobExecutionRepository.countJobExecutionsForToday(jobName, startTime, endTime);
        return count > 0;
    }

	@Override
	public void sendUserMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void clearFiles() {
		atvfftemRepository.deleteAll();
		atvfffreinRepository.deleteAll();
		
	}

	@Override
	public void finalClearFiles() {
		// Limpiar la tabla ATVFFTEM1
        //atvfftem1Repository.deleteAll();
        //System.out.println("Todos los registros de la tabla ATVFFTEM1 han sido eliminados.");

        // Limpiar la tabla ATVFFTEM
        atvfftemRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFTEM han sido eliminados.");

        // Limpiar la tabla ATVFFSAI1
        atvffsa1Repository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFSAI1 han sido eliminados.");

        // Limpiar la tabla ATVFFSAI2
        atvffsa2Repository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFSAI2 han sido eliminados.");

        // Limpiar la tabla ATVFFCHARQ
        atvffcharqRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFCHARQ han sido eliminados.");

        // Limpiar la tabla ATVFFCHSAL
        atvffchsRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFCHSAL han sido eliminados.");

        // Limpiar la tabla ATVFFTITVA
        atvfftitvaRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFTITVA han sido eliminados.");

        // Limpiar la tabla ATVFFMEARQ
        atvffmeaqRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFMEARQ han sido eliminados.");

        // Limpiar la tabla ATVFFMESAL
        atvffmesalRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFMESAL han sido eliminados.");

        // Limpiar la tabla ATVFFCAARQ
        atvffcaaraqRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFCAARQ han sido eliminados.");

        // Limpiar la tabla ATVFFCASAL
        atvffcasalRepository.deleteAll();
        System.out.println("Todos los registros de la tabla ATVFFCASAL han sido eliminados.");

        // Mensaje final
        System.out.println("Limpieza final de registros completada.");
    }
    	

}
