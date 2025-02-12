package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ClientAccountMapper {

    ClientAccountMapper INSTANCE = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(target = "client", ignore = true)  // Prevent recursion
    @Mapping(target = "caseAccount", source = "caseAccount")
    @Mapping(target = "projectAccount", source = "projectAccount")
    @Mapping(target = "retainerAccount", source = "retainerAccount")
    @Mapping(target = "pettyCash", source = "pettyCashActivity")
    @Mapping(target = "activity", source = "activity")
    ClientAccountDto toClientAccountDto(ClientAccount clientAccount);

    @Mapping(target = "client", ignore = true)  // Prevent recursion
    @Mapping(target = "caseAccount", ignore = true)
    @Mapping(target = "projectAccount", ignore = true)
    @Mapping(target = "retainerAccount", ignore = true)
    @Mapping(target = "pettyCashActivity", ignore = true)
    @Mapping(target = "activity", ignore = true)
    ClientAccount toClientAccountEntity(ClientAccountDto clientAccountDto);
    // List conversion methods
    List<ClientAccountDto> toClientAccountDtos(List<ClientAccount> clientAccounts);
    List<ClientAccount> toClientAccountEntities(List<ClientAccountDto> clientAccountDtos);
}
