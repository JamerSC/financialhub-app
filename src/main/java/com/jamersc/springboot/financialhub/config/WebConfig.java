package com.jamersc.springboot.financialhub.config;

import com.jamersc.springboot.financialhub.converter.BankDtoConverter;
import com.jamersc.springboot.financialhub.converter.ContactDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BankDtoConverter bankDtoConverter;

    private final ContactDtoConverter contactDtoConverter;


    public WebConfig(BankDtoConverter bankDtoConverter, ContactDtoConverter contactDtoConverter) {
        this.bankDtoConverter = bankDtoConverter;
        this.contactDtoConverter = contactDtoConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankDtoConverter);
        registry.addConverter(contactDtoConverter);
    }
}
