package com.ibm.gerenciador_tarefas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    private final String url = System.getenv("CROSCONFIG");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a configuração a todos os endpoints em /api/**
                .allowedOrigins(url) // Permite requisições de http://localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Permite esses métodos HTTP
    }
}
