package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactIndividualDto;
import com.jamersc.springboot.financialhub.model.ContactIndividual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface ContactIndividualMapper {

    ContactIndividualMapper INSTANCE = Mappers.getMapper(ContactIndividualMapper.class);

    @Mapping(target = "contactId", source = "contact.contactId")
    ContactIndividualDto toContactIndividualDto(ContactIndividual contactIndividual);

    @Mapping(target = "contact.contactId", source = "contactId") // Uncomment if ContactMapper is available
    ContactIndividual toContactIndividualEntity(ContactIndividualDto contactIndividualDto);
}
