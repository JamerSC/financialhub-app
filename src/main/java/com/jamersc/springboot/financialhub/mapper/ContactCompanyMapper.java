package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactCompanyDto;
import com.jamersc.springboot.financialhub.model.contact.ContactCompany;
import org.springframework.stereotype.Component;

@Component
public class ContactCompanyMapper {

    public static ContactCompany toContactCompanyEntity(ContactCompanyDto contactCompanyDto) {
        if (contactCompanyDto == null) {
            return null;
        }

        ContactCompany contactCompany = new ContactCompany();
        contactCompany.setCompanyId(contactCompanyDto.getCompanyId());
        //contactCompany.setContact(ContactMapper.toContactEntity(contactCompanyDto.getContact()));
        contactCompany.setCompanyName(contactCompanyDto.getCompanyName());
        contactCompany.setRegistrationType(contactCompanyDto.getRegistrationType());
        contactCompany.setRepresentativeName(contactCompanyDto.getRepresentativeName());
        contactCompany.setRepresentativeDesignation(contactCompanyDto.getRepresentativeDesignation());
        contactCompany.setMobileNumber(contactCompanyDto.getMobileNumber());
        contactCompany.setEmailAddress(contactCompanyDto.getEmailAddress());
        contactCompany.setAddress(contactCompanyDto.getAddress());

        return contactCompany;
    }

    public static ContactCompanyDto toContactCompanyDto(ContactCompany contactCompany) {
        if (contactCompany == null) {
            return null;
        }

        ContactCompanyDto contactCompanyDto = new ContactCompanyDto();
        contactCompanyDto.setCompanyId(contactCompany.getCompanyId());
        //contactCompanyDto.setContact(ContactMapper.toContactDto(contactCompany.getContact()));
        contactCompanyDto.setCompanyName(contactCompany.getCompanyName());
        contactCompanyDto.setRegistrationType(contactCompany.getRegistrationType());
        contactCompanyDto.setRepresentativeName(contactCompany.getRepresentativeName());
        contactCompanyDto.setRepresentativeDesignation(contactCompany.getRepresentativeDesignation());
        contactCompanyDto.setMobileNumber(contactCompany.getMobileNumber());
        contactCompanyDto.setEmailAddress(contactCompany.getEmailAddress());
        contactCompanyDto.setAddress(contactCompany.getAddress());

        return contactCompanyDto;
    }

}

