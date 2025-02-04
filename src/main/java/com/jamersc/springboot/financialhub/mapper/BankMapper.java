package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.model.Bank;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankMapper {

    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    @Mapping(target = "bankId", source = "bankId")
    BankDto toBankDto(Bank bank);
    @Mapping(target = "accounts", ignore = true)
    Bank toBankEntity(BankDto bankDto);
    List<BankDto> toBankDtoList(List<Bank> banks);
    List<Bank> toBankEntityList(List<BankDto> bankDtos);

    // Add these methods to handle Bank <-> bankId mapping
    default Long map(Bank bank) {
        return (bank != null) ? bank.getBankId() : null;
    }

    default Bank map(Long bankId) {
        if (bankId == null) {
            return null;
        }
        Bank bank = new Bank();
        bank.setBankId(bankId);
        return bank;
    }
}
