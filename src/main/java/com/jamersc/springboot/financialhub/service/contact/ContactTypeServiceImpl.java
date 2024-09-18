package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactType;
import com.jamersc.springboot.financialhub.repository.ContactTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ContactTypeServiceImpl implements ContactTypeService {

    @Autowired
    private ContactTypeRepository contactTypeRepository;

    @Override
    public List<ContactType> getAllContactTypes() {
        return contactTypeRepository.findAll();
    }

    @Override
    public ContactType getContactTypeById(Long contactTypeId) {
        return contactTypeRepository.findById(contactTypeId).orElseThrow(() -> new RuntimeException("Contact Type ID not found."));
    }

    @Override
    public void save(ContactType contactType) {
        contactTypeRepository.save(contactType);
    }

    @Override
    public void deleteContactTypeById(Long contactTypeId) {
        contactTypeRepository.deleteById(contactTypeId);
    }
}
