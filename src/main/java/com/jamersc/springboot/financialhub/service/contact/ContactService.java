package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.ContactSubType;
import com.jamersc.springboot.financialhub.model.ContactType;

import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts();

    Contact getContactById(Long contactId);

    void save(Contact contact);

    void deleteContactById(Long contactId);

}
