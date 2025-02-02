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

    BankDto toBankDto(Bank bank);
    @Mapping(target = "accounts", ignore = true)
    Bank toBankEntity(BankDto bankDto);
    List<BankDto> toBankDtoList(List<Bank> banks);
    List<Bank> toBankEntityList(List<BankDto> bankDtos);
}
