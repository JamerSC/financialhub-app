package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BankAccountMapper.class})
public interface BankTransactionMapper {

    BankTransactionMapper INSTANCE = Mappers.getMapper(BankTransactionMapper.class);

    @Mapping(target = "bankAccountId", source = "bankAccount.bankAccountId")
    BankTransactionDto toTransactionDto(BankTransaction bankTransaction);

    @Mapping(target = "bankAccount", source = "bankAccountId")
    BankTransaction toTransactionEntity(BankTransactionDto bankTransactionDto);

    List<BankTransactionDto> toBankTransactionDtoList(List<BankTransaction> bankTransactionList);

    List<BankTransaction> toBankTransactionEntityList(List<BankTransactionDto> bankTransactionDtoList);
}
