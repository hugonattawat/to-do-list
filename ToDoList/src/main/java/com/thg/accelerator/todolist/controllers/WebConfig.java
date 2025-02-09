package com.thg.accelerator.todolist.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allows CORS requests to any endpoint
                .allowedOrigins("http://localhost:3000") // Allow only this origin can be replaced with "*" to allow any origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP Methods
                .allowedHeaders("*"); // Allowed headers
    }
}
