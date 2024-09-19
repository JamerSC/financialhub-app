package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.ContactSubCategory;
import com.jamersc.springboot.financialhub.repository.ContactSubTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ContactSubTypeServiceImpl implements ContactSubTypeService {

    @Autowired
    private ContactSubTypeRepository contactSubTypeRepository;

    @Override
    public List<ContactSubCategory> getAllContactSubTypes() {
        return contactSubTypeRepository.findAll();
    }

    @Override
    public ContactSubCategory getContactSubTypeById(Long contactSubTypeId) {
        return contactSubTypeRepository.findById(contactSubTypeId).orElseThrow(() -> new RuntimeException("Contact Sub Type ID not found."));
    }

    @Override
    public void save(ContactSubCategory contactSubCategory) {
        contactSubTypeRepository.save(contactSubCategory);
    }

    @Override
    public void deleteContactSubTypeById(Long contactSubTypeId) {
        contactSubTypeRepository.deleteById(contactSubTypeId);
    }
}
