package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.contact.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts();

    Contact getContactById(Long contactId);

    void saveContactIndividual(Contact contactIndividual, String username);

    void saveContactCompany(Contact contactCompany, String username);

    void deleteContactById(Long contactId);

}
