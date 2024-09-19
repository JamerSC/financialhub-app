package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactSubCategory;

import java.util.List;

public interface ContactSubTypeService {

    List<ContactSubCategory> getAllContactSubTypes();

    ContactSubCategory getContactSubTypeById(Long contactSubTypeId);

    void save(ContactSubCategory contactSubCategory);

    void deleteContactSubTypeById(Long contactSubTypeId);
}
