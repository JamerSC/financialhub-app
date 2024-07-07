package com.jamersc.springboot.financialhub.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        // Serve static resources from the /static/ directory
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // Serve resources from the /templates/ directory (if necessary)
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

        // Serve resources from the /fragments/ directory (if necessary)
        registry.addResourceHandler("/fragments/**")
                .addResourceLocations("classpath:/templates/fragments/");

        // Serve resources from the /layout/ directory (if necessary)
        registry.addResourceHandler("/fragments/layouts/**")
                .addResourceLocations("classpath:/templates/fragments/layouts/");
    }
}
