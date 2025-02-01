package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {BankAccountMapper.class})
//@Component
public interface BankTransactionMapper {
    //class BankTransactionMapper {

    BankTransactionMapper INSTANCE = Mappers.getMapper(BankTransactionMapper.class);

    @Mapping(target = "bankAccount", source = "bankAccount")
    BankTransactionDto toTransactionDto(BankTransaction bankTransaction);

    @Mapping(target = "bankAccount", source = "bankAccount")
    BankTransaction toTransactionEntity(BankTransactionDto bankTransactionDto);

    List<BankTransactionDto> toBankTransactionDtoList(List<BankTransaction> bankTransactionList);

    List<BankTransaction> toBankTransactionEntityList(List<BankTransactionDto> bankTransactionDtoList);

    /*public static BankTransactionDto toTransactionDto(BankTransaction bankTransaction) {
        if (bankTransaction == null) {
            return null;
        }

        BankTransactionDto bankTransactionDto = new BankTransactionDto();
        bankTransactionDto.setId(bankTransactionDto.getId());
        // static method no need for @Autowired or new bean avoid circular dependency resolution
        bankTransactionDto.setBankAccount(BankAccountMapper.toBankAccountDto(bankTransaction.getBankAccount()));
        bankTransactionDto.setTransactionDate(bankTransaction.getTransactionDate());
        bankTransactionDto.setTransactionType(bankTransaction.getTransactionType());
        bankTransactionDto.setTransactionAmount(bankTransaction.getTransactionAmount());
        bankTransactionDto.setTransactionNote(bankTransaction.getTransactionNote());
        bankTransactionDto.setCreatedBy(bankTransaction.getCreatedBy());
        bankTransactionDto.setCreatedAt(bankTransaction.getCreatedAt());
        bankTransactionDto.setUpdatedBy(bankTransaction.getUpdatedBy());
        bankTransactionDto.setUpdatedAt(bankTransaction.getUpdatedAt());

        return bankTransactionDto;
    }

    public static BankTransaction toTransactionEntity(BankTransactionDto bankTransactionDto) {
        if (bankTransactionDto == null) {
            return null;
        }

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setId(bankTransactionDto.getId());
        // static method no need for @Autowired or new bean avoid circular dependency resolution
        bankTransaction.setBankAccount(BankAccountMapper.toBankEntity(bankTransactionDto.getBankAccount()));
        bankTransaction.setTransactionDate(bankTransactionDto.getTransactionDate());
        bankTransaction.setTransactionType(bankTransactionDto.getTransactionType());
        bankTransaction.setTransactionAmount(bankTransactionDto.getTransactionAmount());
        bankTransaction.setTransactionNote(bankTransactionDto.getTransactionNote());
        bankTransaction.setCreatedBy(bankTransactionDto.getCreatedBy());
        bankTransaction.setCreatedAt(bankTransactionDto.getCreatedAt());
        bankTransaction.setUpdatedBy(bankTransactionDto.getUpdatedBy());
        bankTransaction.setUpdatedAt(bankTransactionDto.getUpdatedAt());

        return bankTransaction;
    }

    // Convert List<TransactionDto> to List<Transaction>
    public static List<BankTransaction> toBankTransactionEntityList(List<BankTransactionDto> bankTransactionDtoList) {
        return bankTransactionDtoList.stream()
                .map(BankTransactionMapper::toTransactionEntity)
                .collect(Collectors.toList());
    }

    // Convert List<Transaction> to List<TransactionDto>
    public static List<BankTransactionDto> toBankTransactionDtoList(List<BankTransaction> bankTransactionList) {
        return bankTransactionList.stream()
                .map(BankTransactionMapper::toTransactionDto)
                .collect(Collectors.toList());
    }*/
}
