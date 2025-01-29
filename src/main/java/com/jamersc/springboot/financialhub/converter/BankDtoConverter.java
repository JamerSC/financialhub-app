package com.jamersc.springboot.financialhub.converter;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BankDtoConverter implements Converter<String, BankDto> {

    @Autowired
    private BankService bankService;

    @Override
    public BankDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        Long bankId = Long.valueOf(source);
        return bankService.findBankById(bankId);
    }
}
