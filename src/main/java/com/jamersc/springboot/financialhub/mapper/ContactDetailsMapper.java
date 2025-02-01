package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactDetailsDto;
import com.jamersc.springboot.financialhub.model.ContactDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
// @Component
public interface ContactDetailsMapper {
    //class ContactDetailsMapper {

    ContactDetailsMapper INSTANCE = Mappers.getMapper(ContactDetailsMapper.class);

    @Mapping(source = "contact", target = "contact") // Uncomment if ContactMapper is available
    ContactDetailsDto toContactDetailsDto(ContactDetails contactDetails);

    @Mapping(source = "contact", target = "contact") // Uncomment if ContactMapper is available
    ContactDetails toContactDetailsEntity(ContactDetailsDto contactDetailsDto);



    // Entity to DTO

    /*public static ContactDetailsDto toContactDetailsDto(ContactDetails contactDetails) {
        if (contactDetails == null) {
            return null;
        }

        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setDetailId(contactDetails.getDetailId());
        //contactDetailsDto.setContact(ContactMapper.toContactDto(contactDetails.getContact()));
        contactDetailsDto.setDesignationFor(contactDetails.getDesignationFor());
        contactDetailsDto.setBankName(contactDetails.getBankName());
        contactDetailsDto.setAccountNo(contactDetails.getAccountNo());

        return contactDetailsDto;
    }
    public static ContactDetails toContactDetailsEntity(ContactDetailsDto contactDetailsDto) {
        if (contactDetailsDto == null) {
            return null;
        }

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setDetailId(contactDetailsDto.getDetailId());
        //contactDetails.setContact(ContactMapper.toContactEntity(contactDetailsDto.getContact()));
        contactDetails.setDesignationFor(contactDetailsDto.getDesignationFor());
        contactDetails.setBankName(contactDetailsDto.getBankName());
        contactDetails.setAccountNo(contactDetailsDto.getAccountNo());

        return contactDetails;
    }*/
}
