package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.mapper.ContactCompanyMapper;
import com.jamersc.springboot.financialhub.mapper.ContactDetailsMapper;
import com.jamersc.springboot.financialhub.mapper.ContactIndividualMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactIndividualRepository contactIndividualRepository;
    @Autowired
    private ContactCompanyRepository contactCompanyRepository;
    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public ContactDto getContactById(Long contactId) {
        Contact contact = contactRepository.findById(contactId).orElse(null);
        if (contact != null) {
            ContactDto contactDto = ContactMapper.toContactDto(contact);
            logger.info("Contact ID Details: " + contactDto);
            return contactDto;
        }
        throw new RuntimeException("Contact ID not found.");
    }

    @Override
    public void saveContactIndividual(ContactDto contactIndividual, String username) {
        Contact contact;
        ContactIndividual individual;
        ContactDetails details;

        contact = ContactMapper.toContactEntity(contactIndividual);
        contact.setContactType(ContactType.INDIVIDUAL);
        User createdBy = userRepo.findByUsername(username);

        if (username != null) {
            contact.setCreatedBy(createdBy.getId());
            contact.setUpdatedBy(createdBy.getId());
        }

        logger.info("Saving new contact individual: " + contact);
        contactRepository.save(contact);

        if (contactIndividual.getIndividual() != null){
            individual = ContactIndividualMapper.toContactIndividualEntity(contactIndividual.getIndividual());
            individual.setContact(contact);
            logger.info("Saving contact individual: " + individual);
            contactIndividualRepository.save(individual);
        }

        if (contactIndividual.getAdditionalDetails() != null) {
            details = ContactDetailsMapper.toContactDetailsEntity(contactIndividual.getAdditionalDetails());
            details.setContact(contact);
            logger.info("Saving individual other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void saveContactCompany(ContactDto contactCompany, String username) {
        Contact contact;
        ContactCompany company;
        ContactDetails details;

        contact = ContactMapper.toContactEntity(contactCompany);
        contact.setContactType(ContactType.COMPANY);
        User createdBy = userRepo.findByUsername(username);

        if (createdBy != null) {
            contact.setCreatedBy(createdBy.getId());
            contact.setUpdatedBy(createdBy.getId());
        }

        logger.info("Saving new contact company: " + contact);
        contactRepository.save(contact);

        if (contactCompany.getCompany() != null) {
            company = ContactCompanyMapper.toContactCompanyEntity(contactCompany.getCompany());
            company.setContact(contact);
            logger.info("Saving contact company: " + company);
            contactCompanyRepository.save(company);
        }
        if (contactCompany.getAdditionalDetails() != null) {
            details = ContactDetailsMapper.toContactDetailsEntity(contactCompany.getAdditionalDetails());
            details.setContact(contact);
            logger.info("Saving company other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
