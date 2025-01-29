package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactIndividualDto;
import com.jamersc.springboot.financialhub.model.ContactIndividual;
import org.springframework.stereotype.Component;

@Component
public class ContactIndividualMapper {
    // Entity to DTO
    public static ContactIndividualDto toContactIndividualDto(ContactIndividual contactIndividual) {
        if (contactIndividual == null) {
            return null;
        }

        ContactIndividualDto contactIndividualDto = new ContactIndividualDto();
        contactIndividualDto.setIndividualId(contactIndividual.getIndividualId());
        //contactIndividualDto.setContact(ContactMapper.toContactDto(contactIndividual.getContact()));
        contactIndividualDto.setTitle(contactIndividual.getTitle());
        contactIndividualDto.setLastName(contactIndividual.getLastName());
        contactIndividualDto.setFirstName(contactIndividual.getFirstName());
        contactIndividualDto.setMiddleName(contactIndividual.getMiddleName());
        contactIndividualDto.setSuffix(contactIndividual.getSuffix());
        contactIndividualDto.setMobileNumber(contactIndividual.getMobileNumber());
        contactIndividualDto.setEmailAddress(contactIndividual.getEmailAddress());
        contactIndividualDto.setAddress(contactIndividual.getAddress());

        return contactIndividualDto;
    }

    public static ContactIndividual toContactIndividualEntity(ContactIndividualDto contactIndividualDto) {
        if (contactIndividualDto == null) {
            return null;
        }

        ContactIndividual contactIndividual = new ContactIndividual();
        contactIndividual.setIndividualId(contactIndividualDto.getIndividualId());
        //contactIndividual.setContact(ContactMapper.toContactEntity(contactIndividualDto.getContact()));
        contactIndividual.setTitle(contactIndividualDto.getTitle());
        contactIndividual.setLastName(contactIndividualDto.getLastName());
        contactIndividual.setFirstName(contactIndividualDto.getFirstName());
        contactIndividual.setMiddleName(contactIndividualDto.getMiddleName());
        contactIndividual.setSuffix(contactIndividualDto.getSuffix());
        contactIndividual.setMobileNumber(contactIndividualDto.getMobileNumber());
        contactIndividual.setEmailAddress(contactIndividualDto.getEmailAddress());
        contactIndividual.setAddress(contactIndividualDto.getAddress());

        return contactIndividual;
    }
}
