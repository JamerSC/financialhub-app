package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> getAllContacts();

    ContactDto getContactById(Long contactId);

    Contact findByIdWithAccounts(Long contactId);

    void saveContactIndividual(ContactDto contactIndividual, String username);

    void updateContactIndividual(ContactDto contactIndividual, String username);

    void saveContactCompany(ContactDto contactCompany, String username);

    void updateContactCompany(ContactDto contactCompany, String username);

    void deleteContactById(Long contactId);

}
