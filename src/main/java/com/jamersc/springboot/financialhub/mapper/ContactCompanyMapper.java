package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactCompanyDto;
import com.jamersc.springboot.financialhub.model.ContactCompany;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactCompanyMapper {

    ContactCompanyMapper INSTANCE = Mappers.getMapper(ContactCompanyMapper.class);

    @Mapping(target = "contactId", source = "contact.contactId")
    ContactCompanyDto toContactCompanyDto(ContactCompany contactCompany);

    @Mapping(target = "contact", ignore = true)
    ContactCompany toContactCompanyEntity(ContactCompanyDto contactCompanyDto);
}

