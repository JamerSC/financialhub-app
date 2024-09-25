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
    public void saveContactIndividual(Contact contactIndividual, String username) {
        ContactIndividual individual;
        ContactAdditionalDetails details;
        Contact contactInv = new Contact();
        // saving contact primary info
        contactInv.setContactType(ContactType.INDIVIDUAL);
        contactInv.setContactCategoryType(contactIndividual.getContactCategoryType());
        contactInv.setEngagementDate(contactIndividual.getEngagementDate());
        contactInv.setBestChannelToContact(contactIndividual.getBestChannelToContact());
        User createdBy = userRepo.findByUsername(username);
        if (username != null) {
            contactInv.setCreatedBy(Math.toIntExact(createdBy.getId()));
            contactInv.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        }
        contactRepo.save(contactInv);
        // saving individual details
        if (contactIndividual.getIndividual() != null){
            individual = new ContactIndividual();
            individual.setContact(contactInv);
            individual.setTitle(contactIndividual.getIndividual().getTitle());
            individual.setFirstName(contactIndividual.getIndividual().getFirstName());
            individual.setMiddleName(contactIndividual.getIndividual().getMiddleName());
            individual.setLastName(contactIndividual.getIndividual().getLastName());
            individual.setSuffix(contactIndividual.getIndividual().getSuffix());
            individual.setMobileNumber(contactIndividual.getIndividual().getMobileNumber());
            individual.setEmailAddress(contactIndividual.getIndividual().getEmailAddress());
            individual.setAddress(contactIndividual.getIndividual().getAddress());
            contactIndividualRepo.save(individual);
        }
        // saving individual additional details
        if (contactIndividual.getAdditionalDetails() != null) {
            details = new ContactAdditionalDetails();
            details.setContact(contactInv);
            details.setDesignationFor(contactIndividual.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactIndividual.getAdditionalDetails().getBankName());
            details.setAccountNo(contactIndividual.getAdditionalDetails().getAccountNo());
            contactAdditionalDetailsRepo.save(details);
        }


    }

    @Override
    public void saveContactCompany(Contact contactCompany, String username) {
        User createdBy = userRepo.findByUsername(username);
        Contact contactComp = new Contact();
        contactComp.setCreatedBy(Math.toIntExact(createdBy.getId()));
        contactComp.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        contactRepo.save(contactComp);
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepo.deleteById(contactId);
    }
}
