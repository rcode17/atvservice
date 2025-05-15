package com.bancolombia.pocatv.batch.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
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
                .pageSize(100)
                .build();
    }

	@Bean
    public ItemProcessor<Atvffsai1, Atvfffil> processorSa1() {
        return item -> {
            Atvfffil atvfffil = new Atvfffil();
            LocalDate localDate = LocalDate.parse(String.valueOf(item.getTmfear()), DateTimeFormatter.BASIC_ISO_DATE);
            atvfffil.setTmfear(new Date());
            atvfffil.setTmcdsu(item.getTmcdsu());
            atvfffil.setTmcopr(item.getTmcopr());
            atvfffil.setTmcodo(item.getTmcodo());
            atvfffil.setTmsfar(item.getTmsfar() != null ? new BigDecimal(item.getTmsfar().doubleValue()) : BigDecimal.ZERO);
            atvfffil.setTmsuc(item.getTmsuc());
            atvfffil.setTmcdsuf(item.getTmcdsuf());
            atvfffil.setTmprcu(item.getTmprcu());
            atvfffil.setTmcedcn(item.getTmcedcn());
            atvfffil.setTmpear(item.getTmpear());
            atvfffil.setTmcedan(item.getTmcedan());
            atvfffil.setTmsubg(item.getTmsubg());
            atvfffil.setTmcesbn(item.getTmcesbn());
            atvfffil.setTmsfar(new BigDecimal(item.getTmsfar().doubleValue()));
            atvfffil.setTmdif(new BigDecimal(item.getTmdif().doubleValue()));
            atvfffil.setTmres(item.getTmres());
            atvfffil.setTmobs(item.getTmobs());
            atvfffil.setTmobso(item.getTmobso());
            atvfffil.setTmhora(item.getTmhora());
            atvfffil.setTmsuctx(item.getTmsuctx());

            return atvfffil;
        };
    }

	@Bean
    public ItemWriter<Atvfffil> writerSa1(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Atvfffil> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory); 
        return writer;
    }

    @Bean
    public ItemReader<Atvffsai2> readerSa2(EntityManagerFactory entityManagerFactory) {
        // Implementación de tu ItemReader para Atvffsai2
        return new JpaPagingItemReaderBuilder<Atvffsai2>()
                .name("readerSa2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Atvffsai2 a")
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<Atvffsai2, Atvfftem> processorSa2() {
        return item -> {
            Atvfftem atvfftem = new Atvfftem();

            atvfftem.setTmcdsu(item.getTmcdsu() != null ? item.getTmcdsu().intValue() : null);
            atvfftem.setTmcopr(item.getTmcopr());
            atvfftem.setTmcodo(item.getTmcodo());

            // Conversión segura de entero yyyyMMdd a Date
            LocalDate localDate = LocalDate.parse(String.valueOf(item.getTmfear()), DateTimeFormatter.BASIC_ISO_DATE);
            atvfftem.setTmfear(localDate);

            atvfftem.setTmsuc(item.getTmsuc());
            atvfftem.setTmcdsuf(item.getTmcdsuf() != null ? item.getTmcdsuf().intValue() : null);
            atvfftem.setTmprcu(item.getTmprcu());
            atvfftem.setTmcedcn(item.getTmcedcn());
            atvfftem.setTmpear(item.getTmpear());
            atvfftem.setTmcedan(item.getTmcedan());
            atvfftem.setTmsubg(item.getTmsubg());
            atvfftem.setTmcesbn(item.getTmcesbn());
            atvfftem.setTmsfar(item.getTmsfar());
            atvfftem.setTmdif(item.getTmdif());
            atvfftem.setTmres(item.getTmres());
            atvfftem.setTmobs(item.getTmobs());
            atvfftem.setTmobso(item.getTmobso());
            atvfftem.setTmsfeb(item.getTmsfeb());
            atvfftem.setTmdeb(item.getTmdeb());
            atvfftem.setTmsfev(item.getTmsfev());
            atvfftem.setTmdev(item.getTmdev());
            atvfftem.setTmsfet(item.getTmsfet());
            atvfftem.setTmhora(item.getTmhora() != null ? item.getTmhora().intValue() : null);
            atvfftem.setTmtrans("0144");

            return atvfftem;
        };
    }

    @Bean
    public ItemWriter<Atvfftem> writerSa2(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Atvfftem> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory); 
        return writer;
    }


}
