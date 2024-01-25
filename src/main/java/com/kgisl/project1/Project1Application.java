package com.kgisl.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Project1Application implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
}
