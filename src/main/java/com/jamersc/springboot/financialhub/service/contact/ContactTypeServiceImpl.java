package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactCategory;
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
    public List<ContactCategory> getAllContactTypes() {
        return contactTypeRepository.findAll();
    }

    @Override
    public ContactCategory getContactTypeById(Long contactTypeId) {
        return contactTypeRepository.findById(contactTypeId).orElseThrow(() -> new RuntimeException("Contact Type ID not found."));
    }

    @Override
    public void save(ContactCategory contactCategory) {
        contactTypeRepository.save(contactCategory);
    }

    @Override
    public void deleteContactTypeById(Long contactTypeId) {
        contactTypeRepository.deleteById(contactTypeId);
    }
}
