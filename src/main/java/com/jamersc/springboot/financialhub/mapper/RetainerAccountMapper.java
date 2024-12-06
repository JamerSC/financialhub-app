package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.RetainerAccountDto;
import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.springframework.stereotype.Component;

@Component
public class RetainerAccountMapper {

    // Convert Entity to DTO
    public static RetainerAccountDto toRetainerAccountDto(RetainerAccount retainerAccount) {
        if (retainerAccount == null) {
            return null;
        }

        RetainerAccountDto retainerAccountDto = new RetainerAccountDto();
        retainerAccountDto.setRetainerId(retainerAccount.getRetainerId());
        //retainerAccountDto.setClientAccount(ClientAccountMapper.toClientAccountDto(retainerAccount.getClientAccount())); // Pass the related entity
        retainerAccountDto.setRetainerTitle(retainerAccount.getClientAccount().getAccountTitle());
        retainerAccountDto.setStatus(retainerAccount.getStatus());
        retainerAccountDto.setStartDate(retainerAccount.getStartDate());
        retainerAccountDto.setEndDate(retainerAccount.getEndDate());

        return retainerAccountDto;
    }

    // Convert DTO to Entity
    public static RetainerAccount toRetainerAccountEntity(RetainerAccountDto retainerAccountDto) {
        if (retainerAccountDto == null) {
            return null;
        }

        RetainerAccount retainerAccount = new RetainerAccount();
        retainerAccount.setRetainerId(retainerAccountDto.getRetainerId());
        //retainerAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(retainerAccountDto.getClientAccount())); // Pass the related entity
        retainerAccount.setRetainerTitle(retainerAccountDto.getClientAccount().getAccountTitle());
        retainerAccount.setStatus(retainerAccountDto.getStatus());
        retainerAccount.setStartDate(retainerAccountDto.getStartDate());
        retainerAccount.setEndDate(retainerAccountDto.getEndDate());

        return retainerAccount;
    }
}
