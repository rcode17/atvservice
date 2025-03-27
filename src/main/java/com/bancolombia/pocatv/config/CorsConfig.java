package com.bancolombia.pocatv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // Permitir cualquier origen
                        //.allowedOriginPatterns("*") // Alternativa más flexible
                        .allowedMethods("*") // Permitir cualquier método HTTP
                        .allowedHeaders("*") // Permitir cualquier encabezado
                        .allowCredentials(true); // Habilita credenciales (cookies, auth headers)
            }
        };
    }
}
