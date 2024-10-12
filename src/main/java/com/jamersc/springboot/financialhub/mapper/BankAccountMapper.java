package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BankAccountMapper {

    @Autowired
    private BankMapper bankMapper;

    public BankAccount toBankEntity(BankAccountDto bankAccountDto) {

        if (bankAccountDto == null) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(bankAccountDto.getBankAccountId());
        bankAccount.setBank(bankMapper.toBankEntity(bankAccountDto.getBank())); // change DTO to Entity
        bankAccount.setAccountHolderName(bankAccountDto.getAccountHolderName());
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setAccountBalance(bankAccountDto.getAccountBalance());
        //bankAccount.setTransactions(bankAccountDto.getTransactions());
        bankAccount.setCreatedBy(bankAccountDto.getCreatedBy());
        bankAccount.setCreatedAt(bankAccountDto.getCreatedAt());
        bankAccount.setUpdatedBy(bankAccountDto.getUpdatedBy());
        bankAccount.setUpdatedAt(bankAccountDto.getUpdatedAt());

        return bankAccount;
    }

    public BankAccountDto toBankAccountDto(BankAccount bankAccount) {

        if (bankAccount == null) {
            return null;
        }

        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setBankAccountId(bankAccount.getBankAccountId());
        bankAccountDto.setBank(bankMapper.toBankDto(bankAccount.getBank())); // change DTO to Entity
        bankAccountDto.setAccountHolderName(bankAccount.getAccountHolderName());
        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setAccountBalance(bankAccount.getAccountBalance());
        //bankAccountDto.setTransactions(bankAccount.getTransactions());
        bankAccountDto.setCreatedBy(bankAccount.getCreatedBy());
        bankAccountDto.setCreatedAt(bankAccount.getCreatedAt());
        bankAccountDto.setUpdatedBy(bankAccount.getUpdatedBy());
        bankAccountDto.setUpdatedAt(bankAccount.getUpdatedAt());

        return bankAccountDto;
    }
}
