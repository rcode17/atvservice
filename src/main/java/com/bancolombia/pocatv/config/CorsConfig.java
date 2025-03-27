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
                        .allowedOriginPatterns("https://atvbancolombia.azurewebsites.net","http://localhost:4200") // Permite cualquier origen sin romper CORS con credenciales
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite cualquier método HTTP
                        .allowedHeaders("*") // Permite cualquier encabezado
                        .allowCredentials(true); // Habilita el uso de cookies y autenticación
            }
        };
    }
}
