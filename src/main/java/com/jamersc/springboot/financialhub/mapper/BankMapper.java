package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.model.Bank;

import org.springframework.stereotype.Component;

@Component
public class BankMapper {
    // Entity to DTO
    public static BankDto toBankDto(Bank bank) {
        if (bank == null) {
            return null;
        }

        BankDto bankDto = new BankDto();
        bankDto.setBankId(bank.getBankId());
        bankDto.setName(bank.getName());
        bankDto.setAbbreviation(bank.getAbbreviation());
        bankDto.setBranch(bank.getBranch());
        bankDto.setCreatedBy(bank.getCreatedBy());
        bankDto.setCreatedAt(bank.getCreatedAt());
        bankDto.setUpdatedBy(bank.getUpdatedBy());
        bankDto.setUpdatedAt(bank.getUpdatedAt());
        return bankDto;
    }
    public static Bank toBankEntity(BankDto bankDto) {
        if (bankDto == null) {
            return null;
        }

        Bank bank = new Bank();
        bank.setBankId(bankDto.getBankId());
        bank.setName(bankDto.getName());
        bank.setAbbreviation(bankDto.getAbbreviation());
        bank.setBranch(bankDto.getBranch());
        bank.setCreatedBy(bankDto.getCreatedBy());
        bank.setCreatedAt(bankDto.getCreatedAt());
        bank.setUpdatedBy(bankDto.getUpdatedBy());
        bank.setUpdatedAt(bankDto.getUpdatedAt());
        return bank;
    }
}
