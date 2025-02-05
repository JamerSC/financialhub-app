package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactDetailsDto;
import com.jamersc.springboot.financialhub.model.ContactDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactDetailsMapper {
    //class ContactDetailsMapper {

    ContactDetailsMapper INSTANCE = Mappers.getMapper(ContactDetailsMapper.class);

        // Uncomment if ContactMapper is available
        @Mapping(target = "contactId", source = "contact.contactId")
        ContactDetailsDto toContactDetailsDto(ContactDetails contactDetails);

    @Mapping(target = "contact", ignore = true) //source = "contact"
        // Uncomment if ContactMapper is available
    ContactDetails toContactDetailsEntity(ContactDetailsDto contactDetailsDto);
}
