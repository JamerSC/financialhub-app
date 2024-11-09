package com.jamersc.springboot.financialhub.config;

import com.jamersc.springboot.financialhub.converter.BankDtoConverter;
import com.jamersc.springboot.financialhub.converter.ContactDtoConverter;
import com.jamersc.springboot.financialhub.converter.StringToClientAccountDtoSetConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BankDtoConverter bankDtoConverter;
    private final ContactDtoConverter contactDtoConverter;
    private final StringToClientAccountDtoSetConverter stringToClientAccountDtoSetConverter;


    public WebConfig(BankDtoConverter bankDtoConverter, ContactDtoConverter contactDtoConverter,
                     StringToClientAccountDtoSetConverter stringToClientAccountDtoSetConverter) {
        this.bankDtoConverter = bankDtoConverter;
        this.contactDtoConverter = contactDtoConverter;
        this.stringToClientAccountDtoSetConverter = stringToClientAccountDtoSetConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankDtoConverter);
        registry.addConverter(contactDtoConverter);
        registry.addConverter(stringToClientAccountDtoSetConverter);
    }
}
