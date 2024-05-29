package com.project.springbootblogapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure handling for the "uploads" directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        // Serve the node_modules directory
        registry.addResourceHandler("/node_modules/**")
                .addResourceLocations("file:node_modules/");

        // Ensure CSS and other static resources are correctly handled
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
}