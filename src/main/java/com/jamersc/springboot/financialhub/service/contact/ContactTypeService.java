package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactType;

import java.util.List;

public interface ContactTypeService {

    List<ContactType> getAllContactTypes();

    ContactType getContactTypeById(Long contactTypeId);

    void save(ContactType contactType);

    void deleteContactTypeById(Long contactTypeId);
}
