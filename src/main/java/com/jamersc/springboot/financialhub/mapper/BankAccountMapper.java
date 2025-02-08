package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BankMapper.class, BankTransactionMapper.class})
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    @Mapping(target = "bankId", source = "bank.bankId")
    @Mapping(target = "transactions", source = "bankTransactions")
    @Mapping(target = "bankAccountId", source = "bankAccountId") // Add this line to map bankAccountId properly
    BankAccountDto toBankAccountDto(BankAccount bankAccount);
    @Mapping(target = "bankTransactions", source = "transactions")
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "bankAccountId", ignore = true)  // Add ignore to prevent issues with ID mapping
    BankAccount toBankAccountEntity(BankAccountDto bankAccountDto);
    List<BankAccountDto> toBankAccountDtoList(List<BankAccount> bankAccounts);
    List<BankAccount> toBankAccountEntityList(List<BankAccountDto> bankAccountDtos);
}
