package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactDetailsDto;
import com.jamersc.springboot.financialhub.model.contact.ContactDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactDetailsMapper {

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
    }

    public static ContactDetailsDto toContactDetailsDto(ContactDetails contactDetails) {
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


}
