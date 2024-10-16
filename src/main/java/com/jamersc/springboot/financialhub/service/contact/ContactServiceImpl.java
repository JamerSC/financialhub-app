package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.mapper.ContactCompanyMapper;
import com.jamersc.springboot.financialhub.mapper.ContactDetailsMapper;
import com.jamersc.springboot.financialhub.mapper.ContactIndividualMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.model.contact.*;
import com.jamersc.springboot.financialhub.repository.*;
import com.jamersc.springboot.financialhub.service.user.UserServiceImpl;
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

        //contact = new Contact();
        contact = ContactMapper.toContactEntity(contactIndividual);
        // saving contact primary info
        contact.setContactType(ContactType.INDIVIDUAL);
        /*contact.setContactCategoryType(contactIndividual.getContactCategoryType());
        contact.setEngagementDate(contactIndividual.getEngagementDate());
        contact.setBestChannelToContact(contactIndividual.getBestChannelToContact());*/
        User createdBy = userRepo.findByUsername(username);
        if (username != null) {
            contact.setCreatedBy(createdBy.getId());
            contact.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new contact individual: " + contact);
        contactRepository.save(contact);
        // saving individual details
        if (contactIndividual.getIndividual() != null){
            //individual = new ContactIndividual();
            individual = ContactIndividualMapper.toContactIndividualEntity(contactIndividual.getIndividual());
            individual.setContact(contact);
            /*individual.setTitle(contactIndividual.getIndividual().getTitle());
            individual.setFirstName(contactIndividual.getIndividual().getFirstName());
            individual.setMiddleName(contactIndividual.getIndividual().getMiddleName());
            individual.setLastName(contactIndividual.getIndividual().getLastName());
            individual.setSuffix(contactIndividual.getIndividual().getSuffix());
            individual.setMobileNumber(contactIndividual.getIndividual().getMobileNumber());
            individual.setEmailAddress(contactIndividual.getIndividual().getEmailAddress());
            individual.setAddress(contactIndividual.getIndividual().getAddress());*/
            logger.info("Saving contact individual: " + individual);
            contactIndividualRepository.save(individual);
        }
        // saving individual additional details
        if (contactIndividual.getAdditionalDetails() != null) {
            //details = new ContactDetails();
            details = ContactDetailsMapper.toContactDetailsEntity(contactIndividual.getAdditionalDetails());
            details.setContact(contact);
            /*details.setDesignationFor(contactIndividual.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactIndividual.getAdditionalDetails().getBankName());
            details.setAccountNo(contactIndividual.getAdditionalDetails().getAccountNo());*/
            logger.info("Saving individual other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void saveContactCompany(ContactDto contactCompany, String username) {
        Contact contact;
        ContactCompany company;
        ContactDetails details;
        //contact = new Contact();
        contact = ContactMapper.toContactEntity(contactCompany);
        contact.setContactType(ContactType.COMPANY);
        /*contact.setContactCategoryType(contactCompany.getContactCategoryType());
        contact.setEngagementDate(contactCompany.getEngagementDate());
        contact.setBestChannelToContact(contactCompany.getBestChannelToContact());*/
        User createdBy = userRepo.findByUsername(username);
        if (createdBy != null) {
            contact.setCreatedBy(createdBy.getId());
            contact.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new contact company: " + contact);
        contactRepository.save(contact);
        // save contact company details
        if (contactCompany.getCompany() != null) {
            //company = new ContactCompany();
            company = ContactCompanyMapper.toContactCompanyEntity(contactCompany.getCompany());
            company.setContact(contact);
            /*company.setCompanyName(contactCompany.getCompany().getCompanyName());
            company.setRegistrationType(contactCompany.getCompany().getRegistrationType());
            company.setRepresentativeName(contactCompany.getCompany().getRepresentativeName());
            company.setRepresentativeDesignation(contactCompany.getCompany().getRepresentativeDesignation());
            company.setMobileNumber(contactCompany.getCompany().getMobileNumber());
            company.setEmailAddress(contactCompany.getCompany().getEmailAddress());
            company.setAddress(contactCompany.getCompany().getAddress());*/
            logger.info("Saving contact company: " + company);
            contactCompanyRepository.save(company);
        }
        if (contactCompany.getAdditionalDetails() != null) {
            //details = new ContactDetails();
            details = ContactDetailsMapper.toContactDetailsEntity(contactCompany.getAdditionalDetails());
            details.setContact(contact);
            /*details.setDesignationFor(contactCompany.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactCompany.getAdditionalDetails().getBankName());
            details.setAccountNo(contactCompany.getAdditionalDetails().getAccountNo());*/
            logger.info("Saving company other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
