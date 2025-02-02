package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BankMapper.class})
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    @Mapping(target = "transactions", source = "bankTransactions")
    BankAccountDto toBankAccountDto(BankAccount bankAccount);
    @Mapping(target = "bankTransactions", source = "transactions")
    @Mapping(target = "bank", source = "bank")
    BankAccount toBankAccountEntity(BankAccountDto bankAccountDto);
    List<BankAccountDto> toBankAccountDtoList(List<BankAccount> bankAccounts);
    List<BankAccount> toBankAccountEntityList(List<BankAccountDto> bankAccountDtos);
}
