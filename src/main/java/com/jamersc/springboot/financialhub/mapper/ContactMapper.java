package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// @Component
@Mapper(uses = {ContactIndividualMapper.class, ContactCompanyMapper.class, ContactDetailsMapper.class, ClientAccountMapper.class, UserMapper.class})
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDto toContactDto(Contact contact);

    @Mapping(target = "user", ignore = true)
    Contact toContactEntity(ContactDto contactDto);


    // Entity to DTO conversion
  /*  public static ContactDto toContactDto(Contact contact) {
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
        //contactDto.setClientAccounts(ClientAccountMapper.toClientAccountDtos(contact.getClientAccounts()));
        contactDto.setAdditionalDetails(ContactDetailsMapper.toContactDetailsDto(contact.getAdditionalDetails()));
        contactDto.setCreatedBy(contact.getCreatedBy());
        contactDto.setCreatedAt(contact.getCreatedAt());
        contactDto.setUpdatedBy(contact.getUpdatedBy());
        contactDto.setUpdatedAt(contact.getUpdatedAt());

        return contactDto;
    }
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
        contact.setIndividual(ContactIndividualMapper.toContactIndividualEntity(contactDto.getIndividual()));
        contact.setCompany(ContactCompanyMapper.toContactCompanyEntity(contactDto.getCompany()));
        contact.setAdditionalDetails(ContactDetailsMapper.toContactDetailsEntity(contactDto.getAdditionalDetails()));
        //contact.setClientAccounts(ClientAccountMapper.toClientAccountEntities(contactDto.getClientAccounts())); // Assuming ClientAccount is mapped as it is.
        contact.setCreatedBy(contactDto.getCreatedBy());
        contact.setCreatedAt(contactDto.getCreatedAt());
        contact.setUpdatedBy(contactDto.getUpdatedBy());
        contact.setUpdatedAt(contactDto.getUpdatedAt());

        return contact;
    }*/
}
