package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(Long contactId) {
        return contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contact ID not found."));
    }

    @Override
    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
