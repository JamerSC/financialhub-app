package com.jamersc.springboot.financialhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BankDtoConverter bankDtoConverter;

    public WebConfig(BankDtoConverter bankDtoConverter) {
        this.bankDtoConverter = bankDtoConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankDtoConverter);
    }
}
