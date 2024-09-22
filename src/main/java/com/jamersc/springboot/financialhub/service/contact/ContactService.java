package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts();

    Contact getContactById(Long contactId);

    void saveIndividual(Contact contact, String username);

    void deleteContactById(Long contactId);

}
