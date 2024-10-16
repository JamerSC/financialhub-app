package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactCompanyDto;
import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.contact.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts();

    ContactDto getContactById(Long contactId);

    void saveContactIndividual(ContactDto contactIndividual, String username);

    void saveContactCompany(ContactDto contactCompany, String username);

    void deleteContactById(Long contactId);

}
