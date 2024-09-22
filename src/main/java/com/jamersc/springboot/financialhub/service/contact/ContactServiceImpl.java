package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.repository.*;
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
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ContactIndividualRepo contactIndividualRepo;

    @Autowired
    private ContactCompanyRepo contactCompanyRepo;

    @Autowired
    private ContactAdditionalDetailsRepo contactAdditionalDetailsRepo;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactById(Long contactId) {
        return contactRepo.findById(contactId).orElseThrow(() -> new RuntimeException("Contact ID not found."));
    }

    @Override
    public void saveIndividual(Contact contact, String username) {
        ContactIndividual individual;
        ContactAdditionalDetails details;
        Contact contactInv = new Contact();
        // saving contact primary info
        contactInv.setContactType(ContactType.INDIVIDUAL);
        contactInv.setContactCategoryType(contact.getContactCategoryType());
        contactInv.setEngagementDate(contact.getEngagementDate());
        contactInv.setBestChannelToContact(contact.getBestChannelToContact());
        User createdBy = userRepo.findByUsername(username);
        if (username != null) {
            contactInv.setCreatedBy(Math.toIntExact(createdBy.getId()));
            contactInv.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        }
        contactRepo.save(contactInv);
        // saving individual details
        if (contact.getIndividual() != null){
            individual = new ContactIndividual();
            individual.setContact(contactInv);
            individual.setTitle(contact.getIndividual().getTitle());
            individual.setFirstName(contact.getIndividual().getFirstName());
            individual.setMiddleName(contact.getIndividual().getMiddleName());
            individual.setLastName(contact.getIndividual().getLastName());
            individual.setSuffix(contact.getIndividual().getSuffix());
            individual.setMobileNumber(contact.getIndividual().getMobileNumber());
            individual.setEmailAddress(contact.getIndividual().getEmailAddress());
            individual.setAddress(contact.getIndividual().getAddress());
            contactIndividualRepo.save(individual);
        }
        // saving individual additional details
        if (contact.getAdditionalDetails() != null) {
            details = new ContactAdditionalDetails();
            details.setContact(contactInv);
            details.setDesignationFor(contact.getAdditionalDetails().getDesignationFor());
            details.setBankName(contact.getAdditionalDetails().getBankName());
            details.setAccountNo(contact.getAdditionalDetails().getAccountNo());
            contactAdditionalDetailsRepo.save(details);
        }


    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepo.deleteById(contactId);
    }
}
