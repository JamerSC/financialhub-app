package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = {BankMapper.class})
//@Component
public interface BankAccountMapper {
    //class BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    @Mapping(target = "transactions", source = "bankTransactions")
    BankAccountDto toBankAccountDto(BankAccount bankAccount);

    @Mapping(target = "bankTransactions", source = "transactions")
    @Mapping(target = "bank", source = "bank")
    BankAccount toBankAccountEntity(BankAccountDto bankAccountDto);

    List<BankAccountDto> toBankAccountDtoList(List<BankAccount> bankAccounts);

    List<BankAccount> toBankAccountEntityList(List<BankAccountDto> bankAccountDtos);



    // Entity to DTO
    /*public static BankAccountDto toBankAccountDto(BankAccount bankAccount) {

        if (bankAccount == null) {
            return null;
        }

        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setBankAccountId(bankAccount.getBankAccountId());
        // static method no need for @Autowired or new bean avoid circular dependency resolution
        bankAccountDto.setBank(BankMapper.toBankDto(bankAccount.getBank())); // change DTO to Entity
        bankAccountDto.setAccountHolderName(bankAccount.getAccountHolderName());
        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setAccountBalance(bankAccount.getAccountBalance());
        *//*bankAccountDto.setTransactions(bankTransactionMapper
                .toBankTransactionDtoList(bankAccount.getBankTransactions()));*//*
        bankAccountDto.setCreatedBy(bankAccount.getCreatedBy());
        bankAccountDto.setCreatedAt(bankAccount.getCreatedAt());
        bankAccountDto.setUpdatedBy(bankAccount.getUpdatedBy());
        bankAccountDto.setUpdatedAt(bankAccount.getUpdatedAt());

        return bankAccountDto;
    }

    public static BankAccount toBankEntity(BankAccountDto bankAccountDto) {

        if (bankAccountDto == null) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBankAccountId(bankAccountDto.getBankAccountId());
        // static method no need for @Autowired or new bean avoid circular dependency resolution
        bankAccount.setBank(BankMapper.toBankEntity(bankAccountDto.getBank())); // change DTO to Entity
        bankAccount.setAccountHolderName(bankAccountDto.getAccountHolderName());
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setAccountBalance(bankAccountDto.getAccountBalance());
        *//*bankAccount.setBankTransactions(bankTransactionMapper
                .toBankTransactionEntityList(bankAccountDto.getTransactions()));*//*
        bankAccount.setCreatedBy(bankAccountDto.getCreatedBy());
        bankAccount.setCreatedAt(bankAccountDto.getCreatedAt());
        bankAccount.setUpdatedBy(bankAccountDto.getUpdatedBy());
        bankAccount.setUpdatedAt(bankAccountDto.getUpdatedAt());

        return bankAccount;
    }*/
}
