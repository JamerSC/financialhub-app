package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactSubType;
import com.jamersc.springboot.financialhub.model.ContactType;

import java.util.List;

public interface ContactSubTypeService {

    List<ContactSubType> getAllContactSubTypes();

    ContactSubType getContactSubTypeById(Long contactSubTypeId);

    void save(ContactSubType contactSubType);

    void deleteContactSubTypeById(Long contactSubTypeId);
}
