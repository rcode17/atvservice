package com.bancolombia.pocatv.batch.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bancolombia.pocatv.model.Atvfffil;
import com.bancolombia.pocatv.model.Atvffsai1;
import com.bancolombia.pocatv.model.Atvffsai2;
import com.bancolombia.pocatv.model.Atvfftem;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchComponentsConfigAtvoCargar {
	
	@Bean
    public ItemReader<Atvffsai1> readerSa1(EntityManagerFactory entityManagerFactory) {
        // Implementación de tu ItemReader para Atvffsai1
        return new JpaPagingItemReaderBuilder<Atvffsai1>()
                .name("readerSa1")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Atvffsai1 a")
                .pageSize(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Atvffsai1, Atvfffil> processorSa1() {
        // Implementación de tu ItemProcessor para Atvffsai1 a Atvfffil
        return item -> {
            // Lógica de procesamiento
            return null;
        };
    }

    @Bean
    public ItemWriter<Atvfffil> writerSa1() {
        // Implementación de tu ItemWriter para Atvfffil
        return items -> {
            // Lógica para escribir los items procesados
            items.forEach(System.out::println);
        };
    }

    @Bean
    public ItemReader<Atvffsai2> readerSa2(EntityManagerFactory entityManagerFactory) {
        // Implementación de tu ItemReader para Atvffsai2
        return new JpaPagingItemReaderBuilder<Atvffsai2>()
                .name("readerSa2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Atvffsai2 a")
                .pageSize(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Atvffsai2, Atvfftem> processorSa2() {
        // Implementación de tu ItemProcessor para Atvffsai2 a Atvfftem
        return item -> {
            // Lógica de procesamiento
            return null;
        };
    }

    @Bean
    public ItemWriter<Atvfftem> writerSa2() {
        // Implementación de tu ItemWriter para Atvfftem
        return items -> {
            // Lógica para escribir los items procesados
            items.forEach(System.out::println);
        };
    }


}
