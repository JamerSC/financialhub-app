package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.RetainerAccountDto;
import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ClientAccountMapper.class })
public interface RetainerAccountMapper {

    RetainerAccountMapper INSTANCE = Mappers.getMapper(RetainerAccountMapper.class);

    @Mapping(source = "clientAccount", target = "clientAccount")
    @Mapping(source = "clientAccount.accountTitle", target = "retainerTitle")
    RetainerAccountDto toRetainerAccountDto(RetainerAccount retainerAccount);

    @Mapping(source = "clientAccount", target = "clientAccount")
    @Mapping(source = "clientAccount.accountTitle", target = "retainerTitle")
    RetainerAccount toRetainerAccountEntity(RetainerAccountDto retainerAccountDto);
}
