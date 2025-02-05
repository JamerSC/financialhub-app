package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.CaseAccountDto;
import com.jamersc.springboot.financialhub.model.CaseAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientAccountMapper.class})
public interface CaseAccountMapper {

    CaseAccountMapper INSTANCE = Mappers.getMapper(CaseAccountMapper.class);
    @Mapping(target = "clientAccountId", source = "clientAccount.clientAccountId")
    CaseAccountDto toCaseAccountDto(CaseAccount caseAccount);
    @Mapping(target = "clientAccount", ignore = true)
    CaseAccount toCaseAccountEntity(CaseAccountDto caseAccountDto);
}
