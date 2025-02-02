package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactCompanyDto;
import com.jamersc.springboot.financialhub.model.ContactCompany;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactCompanyMapper {

    ContactCompanyMapper INSTANCE = Mappers.getMapper(ContactCompanyMapper.class);

    ContactCompanyDto toContactCompanyDto(ContactCompany contactCompany);

    ContactCompany toContactCompanyEntity(ContactCompanyDto contactCompanyDto);
}

