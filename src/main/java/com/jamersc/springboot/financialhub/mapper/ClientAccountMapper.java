package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientAccountMapper {

    public static ClientAccount toClientAccountEntity(ClientAccountDto clientAccountDto) {
        if (clientAccountDto == null) {
            return null;
        }

        ClientAccount clientAccount = new ClientAccount();
        clientAccount.setClientAccountId(clientAccountDto.getClientAccountId());
        clientAccount.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient())); // many to one
        clientAccount.setAccountTitle(clientAccount.getAccountTitle());
        clientAccount.setClientAccountType(clientAccountDto.getClientAccountType());
        clientAccount.setCaseAccount(CaseAccountMapper.toCaseAccountEntity(clientAccountDto.getCaseAccount())); // one to one
        clientAccount.setCreatedBy(clientAccountDto.getCreatedBy());
        clientAccount.setCreatedAt(clientAccountDto.getCreatedAt());
        clientAccount.setUpdatedBy(clientAccountDto.getUpdatedBy());
        clientAccount.setUpdatedAt(clientAccountDto.getUpdatedAt());
        return  clientAccount;
    }

    public static ClientAccountDto toClientAccountDto(ClientAccount clientAccount) {
        if (clientAccount == null) {
            return null;
        }

        ClientAccountDto clientAccountDto = new ClientAccountDto();
        clientAccountDto.setClientAccountId(clientAccount.getClientAccountId());
        clientAccountDto.setClient(ContactMapper.toContactDto(clientAccount.getClient())); // many to one
        clientAccountDto.setAccountTitle(clientAccount.getAccountTitle());
        clientAccountDto.setClientAccountType(clientAccount.getClientAccountType());
        clientAccountDto.setCaseAccount(CaseAccountMapper.toCaseAccountDto(clientAccount.getCaseAccount())); // one to one
        clientAccountDto.setCreatedBy(clientAccount.getCreatedBy());
        clientAccountDto.setCreatedAt(clientAccount.getCreatedAt());
        clientAccountDto.setUpdatedBy(clientAccount.getUpdatedBy());
        clientAccountDto.setUpdatedAt(clientAccount.getUpdatedAt());
        return  clientAccountDto;
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
    }
}
