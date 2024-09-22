package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.repository.ContactRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactById(Long contactId) {
        return contactRepo.findById(contactId).orElseThrow(() -> new RuntimeException("Contact ID not found."));
    }

    @Override
    public void save(Contact contact) {
        contactRepo.save(contact);
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepo.deleteById(contactId);
    }
}
