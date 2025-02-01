package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
@Mapper
public interface ClientAccountMapper {

    ClientAccountMapper INSTANCE = Mappers.getMapper(ClientAccountMapper.class);

    // Convert to DTO

    @Mapping(target = "pettyCash", source = "pettyCashActivity")
    ClientAccountDto toClientAccountDto(ClientAccount clientAccount);

    // Convert to entity

    @Mapping(target = "pettyCashActivity", source = "pettyCash")
    ClientAccount toClientAccountEntity(ClientAccountDto clientAccountDto);

    // List conversion methods
    List<ClientAccountDto> toClientAccountDtos(List<ClientAccount> clientAccounts);

    List<ClientAccount> toClientAccountEntities(List<ClientAccountDto> clientAccountDtos);

    // Convert to Dto
   /* public static ClientAccountDto toClientAccountDto(ClientAccount clientAccount) {
        if (clientAccount == null) {
            return null;
        }

        ClientAccountDto clientAccountDto = new ClientAccountDto();
        clientAccountDto.setClientAccountId(clientAccount.getClientAccountId());
        // Contact or Client
        clientAccountDto.setClient(ContactMapper.toContactDto(clientAccount.getClient())); // many to one
        clientAccountDto.setAccountTitle(clientAccount.getAccountTitle());
        clientAccountDto.setClientAccountType(clientAccount.getClientAccountType());
        // Case, Project, & Retainer Dto
        clientAccountDto.setCaseAccount(CaseAccountMapper.toCaseAccountDto(clientAccount.getCaseAccount())); // one to one
        clientAccountDto.setProjectAccount(ProjectAccountMapper.toProjectAccountDto(clientAccount.getProjectAccount())); // one to one
        clientAccountDto.setRetainerAccount(RetainerAccountMapper.toRetainerAccountDto(clientAccount.getRetainerAccount())); // one to one
        clientAccountDto.setCreatedBy(clientAccount.getCreatedBy());
        clientAccountDto.setCreatedAt(clientAccount.getCreatedAt());
        clientAccountDto.setUpdatedBy(clientAccount.getUpdatedBy());
        clientAccountDto.setUpdatedAt(clientAccount.getUpdatedAt());
        return  clientAccountDto;
    }

    // Convert to entity
    public static ClientAccount toClientAccountEntity(ClientAccountDto clientAccountDto) {
        if (clientAccountDto == null) {
            return null;
        }

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setClientAccountId(clientAccountDto.getClientAccountId());
        //Contact or Client
        clientAccount.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient())); // many to one
        clientAccount.setAccountTitle(clientAccount.getAccountTitle());
        clientAccount.setClientAccountType(clientAccountDto.getClientAccountType());
        // Case, Project, & Retainer Entity
        clientAccount.setCaseAccount(CaseAccountMapper.toCaseAccountEntity(clientAccountDto.getCaseAccount())); // one to one
        clientAccount.setProjectAccount(ProjectAccountMapper.toProjectAccountEntity(clientAccountDto.getProjectAccount())); // one to one
        clientAccount.setRetainerAccount(RetainerAccountMapper.toRetainerAccountEntity(clientAccountDto.getRetainerAccount())); // one to one
        clientAccount.setCreatedBy(clientAccountDto.getCreatedBy());
        clientAccount.setCreatedAt(clientAccountDto.getCreatedAt());
        clientAccount.setUpdatedBy(clientAccountDto.getUpdatedBy());
        clientAccount.setUpdatedAt(clientAccountDto.getUpdatedAt());
        return  clientAccount;
    }

    // List conversion methods
    public static List<ClientAccount> toClientAccountEntities(List<ClientAccountDto> clientAccountDtos) {
        return clientAccountDtos.stream()
                .map(ClientAccountMapper::toClientAccountEntity)
                .collect(Collectors.toList());
    }

    public static List<ClientAccountDto> toClientAccountDtos(List<ClientAccount> clientAccounts) {
        return clientAccounts.stream()
                .map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toList());
    }*/
}
