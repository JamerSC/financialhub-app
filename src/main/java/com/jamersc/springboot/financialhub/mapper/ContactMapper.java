package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.contact.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    // DTO to Entity conversion
    public static Contact toContactEntity(ContactDto contactDto) {
        if (contactDto == null) {
            return null;
        }

        Contact contact = new Contact();
        contact.setContactId(contactDto.getContactId());
        contact.setContactType(contactDto.getContactType());
        contact.setContactCategoryType(contactDto.getContactCategoryType());
        contact.setEngagementDate(contactDto.getEngagementDate());
        contact.setBestChannelToContact(contactDto.getBestChannelToContact());
        //contact.setIndividual(ContactIndividualMapper.toContactIndividualEntity(contactDto.getIndividual()));
        //contact.setCompany(ContactCompanyMapper.toContactCompanyEntity(contactDto.getCompany()));
        //contact.setAdditionalDetails(ContactDetailsMapper.toContactDetailsEntity(contactDto.getAdditionalDetails()));
        //contact.setClientAccounts(contactDto.getClientAccounts()); // Assuming ClientAccount is mapped as it is.
        contact.setCreatedBy(contactDto.getCreatedBy());
        contact.setCreatedAt(contactDto.getCreatedAt());
        contact.setUpdatedBy(contactDto.getUpdatedBy());
        contact.setUpdatedAt(contactDto.getUpdatedAt());

        return contact;
    }

    // Entity to DTO conversion
    public static ContactDto toContactDto(Contact contact) {
        if (contact == null) {
            return null;
        }

        ContactDto contactDto = new ContactDto();
        contactDto.setContactId(contact.getContactId());
        contactDto.setContactType(contact.getContactType());
        contactDto.setContactCategoryType(contact.getContactCategoryType());
        contactDto.setEngagementDate(contact.getEngagementDate());
        contactDto.setBestChannelToContact(contact.getBestChannelToContact());
        contactDto.setIndividual(ContactIndividualMapper.toContactIndividualDto(contact.getIndividual()));
        contactDto.setCompany(ContactCompanyMapper.toContactCompanyDto(contact.getCompany()));
        contactDto.setAdditionalDetails(ContactDetailsMapper.toContactDetailsDto(contact.getAdditionalDetails()));
        //contactDto.setClientAccounts(contact.getClientAccounts()); // Assuming ClientAccount is already mapped.
        contactDto.setCreatedBy(contact.getCreatedBy());
        contactDto.setCreatedAt(contact.getCreatedAt());
        contactDto.setUpdatedBy(contact.getUpdatedBy());
        contactDto.setUpdatedAt(contact.getUpdatedAt());

        return contactDto;
    }



}
