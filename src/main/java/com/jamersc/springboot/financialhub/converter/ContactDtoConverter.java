package com.jamersc.springboot.financialhub.converter;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Component
public class ContactDtoConverter { // implements Converter<String, ContactDto> {

   /* @Autowired
    private ContactService contactService;

    @Override
    public ContactDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        Long contactId = Long.valueOf(source);
        return contactService.getContactById(contactId);
    }*/
}
