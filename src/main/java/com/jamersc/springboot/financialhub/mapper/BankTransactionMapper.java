package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.model.bank.BankTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankTransactionMapper {

    @Autowired
    private BankAccountMapper bankAccountMapper;

    public BankTransaction toTransactionEntity(BankTransactionDto bankTransactionDto) {
        if (bankTransactionDto == null) {
            return null;
        }

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setId(bankTransactionDto.getId());
        bankTransaction.setBankAccount(bankAccountMapper.toBankEntity(bankTransactionDto.getBankAccount()));
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

    public BankTransactionDto toTransactionDto(BankTransaction bankTransaction) {
        if (bankTransaction == null) {
            return null;
        }

        BankTransactionDto bankTransactionDto = new BankTransactionDto();
        bankTransactionDto.setId(bankTransactionDto.getId());
        bankTransactionDto.setBankAccount(bankAccountMapper.toBankAccountDto(bankTransaction.getBankAccount()));
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

    // Convert List<TransactionDto> to List<Transaction>
    public List<BankTransaction> toBankTransactionEntityList(List<BankTransactionDto> bankTransactionDtoList) {
        return bankTransactionDtoList.stream()
                .map(this::toTransactionEntity)
                .collect(Collectors.toList());
    }

    // Convert List<Transaction> to List<TransactionDto>
    public List<BankTransactionDto> toBankTransactionDtoList(List<BankTransaction> bankTransactionList) {
        return bankTransactionList.stream()
                .map(this::toTransactionDto)
                .collect(Collectors.toList());
    }

}
