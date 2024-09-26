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
    private UserRepository userRepo;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactIndividualRepository contactIndividualRepository;

    @Autowired
    private ContactCompanyRepository contactCompanyRepository;

    @Autowired
    private ContactAdditionalDetailsRepository contactAdditionalDetailsRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(Long contactId) {
        return contactRepository.findById(contactId).orElseThrow(() -> new RuntimeException("Contact ID not found."));
    }

    @Override
    public void saveContactIndividual(Contact contactIndividual, String username) {
        ContactIndividual individual;
        ContactAdditionalDetails details;
        Contact tempContactIndividual = new Contact();
        // saving contact primary info
        tempContactIndividual.setContactType(ContactType.INDIVIDUAL);
        tempContactIndividual.setContactCategoryType(contactIndividual.getContactCategoryType());
        tempContactIndividual.setEngagementDate(contactIndividual.getEngagementDate());
        tempContactIndividual.setBestChannelToContact(contactIndividual.getBestChannelToContact());
        User createdBy = userRepo.findByUsername(username);
        if (username != null) {
            tempContactIndividual.setCreatedBy(Math.toIntExact(createdBy.getId()));
            tempContactIndividual.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        }
        contactRepository.save(tempContactIndividual);
        // saving individual details
        if (contactIndividual.getIndividual() != null){
            individual = new ContactIndividual();
            individual.setContact(tempContactIndividual);
            individual.setTitle(contactIndividual.getIndividual().getTitle());
            individual.setFirstName(contactIndividual.getIndividual().getFirstName());
            individual.setMiddleName(contactIndividual.getIndividual().getMiddleName());
            individual.setLastName(contactIndividual.getIndividual().getLastName());
            individual.setSuffix(contactIndividual.getIndividual().getSuffix());
            individual.setMobileNumber(contactIndividual.getIndividual().getMobileNumber());
            individual.setEmailAddress(contactIndividual.getIndividual().getEmailAddress());
            individual.setAddress(contactIndividual.getIndividual().getAddress());
            contactIndividualRepository.save(individual);
        }
        // saving individual additional details
        if (contactIndividual.getAdditionalDetails() != null) {
            details = new ContactAdditionalDetails();
            details.setContact(tempContactIndividual);
            details.setDesignationFor(contactIndividual.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactIndividual.getAdditionalDetails().getBankName());
            details.setAccountNo(contactIndividual.getAdditionalDetails().getAccountNo());
            contactAdditionalDetailsRepository.save(details);
        }


    }

    @Override
    public void saveContactCompany(Contact contactCompany, String username) {
        ContactCompany company;
        ContactAdditionalDetails details;
        Contact tempContactCompany = new Contact();
        tempContactCompany.setContactType(ContactType.COMPANY);
        tempContactCompany.setContactCategoryType(contactCompany.getContactCategoryType());
        tempContactCompany.setEngagementDate(contactCompany.getEngagementDate());
        tempContactCompany.setBestChannelToContact(contactCompany.getBestChannelToContact());
        User createdBy = userRepo.findByUsername(username);
        if (createdBy != null) {
            tempContactCompany.setCreatedBy(Math.toIntExact(createdBy.getId()));
            tempContactCompany.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        }
        contactRepository.save(tempContactCompany);
        // save contact company details
        if (contactCompany.getCompany() != null) {
            company = new ContactCompany();
            company.setContact(tempContactCompany);
            company.setCompanyName(contactCompany.getCompany().getCompanyName());
            company.setRegistrationType(contactCompany.getCompany().getRegistrationType());
            company.setRepresentativeName(contactCompany.getCompany().getRepresentativeName());
            company.setRepresentativeDesignation(contactCompany.getCompany().getRepresentativeDesignation());
            company.setMobileNumber(contactCompany.getCompany().getMobileNumber());
            company.setEmailAddress(contactCompany.getCompany().getEmailAddress());
            company.setAddress(contactCompany.getCompany().getAddress());
            contactCompanyRepository.save(company);
        }
        if (contactCompany.getAdditionalDetails() != null) {
            details = new ContactAdditionalDetails();
            details.setContact(tempContactCompany);
            details.setDesignationFor(contactCompany.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactCompany.getAdditionalDetails().getBankName());
            details.setAccountNo(contactCompany.getAdditionalDetails().getAccountNo());
            contactAdditionalDetailsRepository.save(details);
        }
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
