package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.CaseAccountDto;
import com.jamersc.springboot.financialhub.model.CaseAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ClientAccountMapper.class})
public interface CaseAccountMapper {

    CaseAccountMapper INSTANCE = Mappers.getMapper(CaseAccountMapper.class);

    // Convert Entity to DTO
    @Mapping(source = "clientAccount", target = "clientAccount") // Maps the related ClientAccount entity
    @Mapping(source = "caseType", target = "caseType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    CaseAccountDto toCaseAccountDto(CaseAccount caseAccount);

    // Convert DTO to Entity
    @Mapping(source = "clientAccount", target = "clientAccount") // Maps the related ClientAccount DTO
    @Mapping(source = "caseType", target = "caseType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    CaseAccount toCaseAccountEntity(CaseAccountDto caseAccountDto);
}
