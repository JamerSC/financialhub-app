package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ClientAccountMapper {

    ClientAccountMapper INSTANCE = Mappers.getMapper(ClientAccountMapper.class);

    @Mapping(target = "pettyCash", source = "pettyCashActivity")
    ClientAccountDto toClientAccountDto(ClientAccount clientAccount);
    @Mapping(target = "pettyCashActivity", source = "pettyCash")
    ClientAccount toClientAccountEntity(ClientAccountDto clientAccountDto);
    // List conversion methods
    List<ClientAccountDto> toClientAccountDtos(List<ClientAccount> clientAccounts);
    List<ClientAccount> toClientAccountEntities(List<ClientAccountDto> clientAccountDtos);
}
