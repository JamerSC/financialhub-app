package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.Contact;

import java.util.List;

public interface ContactService {

    List<ContactDto> getAllContacts();

    ContactDto getContactById(Long contactId);

    List<ContactDto> getContactsWithInternalCategory();

    Contact findByIdWithAccounts(Long contactId);

    void saveContactIndividual(ContactDto contactIndividual, String username);

    void updateContactIndividual(ContactDto contactIndividual, String username);

    void saveContactCompany(ContactDto contactCompany, String username);

    void updateContactCompany(ContactDto contactCompany, String username);

    void deleteContactById(Long contactId);

}
