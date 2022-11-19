package com.example.backend.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class AngularConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry e){
        e.addMapping("/**").allowedMethods("*");
    }

}
