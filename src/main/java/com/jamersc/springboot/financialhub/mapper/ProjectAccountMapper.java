package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ProjectAccountDto;
import com.jamersc.springboot.financialhub.model.ProjectAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ClientAccountMapper.class })
public interface ProjectAccountMapper {

    ProjectAccountMapper INSTANCE = Mappers.getMapper(ProjectAccountMapper.class);

    @Mapping(target = "clientAccountId", source = "clientAccount.clientAccountId")
    ProjectAccountDto toProjectAccountDto(ProjectAccount projectAccount);

    @Mapping(target = "clientAccount", ignore = true)
    ProjectAccount toProjectAccountEntity(ProjectAccountDto projectAccountDto);
}
