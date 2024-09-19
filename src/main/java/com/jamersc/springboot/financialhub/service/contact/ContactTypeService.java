package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactCategory;

import java.util.List;

public interface ContactTypeService {

    List<ContactCategory> getAllContactTypes();

    ContactCategory getContactTypeById(Long contactTypeId);

    void save(ContactCategory contactCategory);

    void deleteContactTypeById(Long contactTypeId);
}
