package com.jamersc.springboot.financialhub.service.contact;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactIndividualRepository contactIndividualRepository;
    @Autowired
    private ContactCompanyRepository contactCompanyRepository;
    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Override
    public List<ContactDto> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(ContactMapper::toContactDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact ID not found!"));
        logger.info("Contact ID Details: " + contact);
        return ContactMapper.toContactDto(contact);
    }

    @Override
    public List<ContactDto> getContactsWithInternalCategory() {
        return contactRepository.findContactsWithInternalCategory()
                .stream()
                .map(ContactMapper::toContactDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDto> findContactsWithInternalCategoryAndNullUser() {
        return contactRepository.findContactsWithInternalCategoryAndNullUser()
                .stream()
                .map(ContactMapper::toContactDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDto> findContactsWithInternalCategoryAndNotNullUser() {
        return contactRepository.findContactsWithInternalCategoryAndNotNullUser()
                .stream()
                .map(ContactMapper::toContactDto)
                .collect(Collectors.toList());
    }


    @Override
    public Contact findByIdWithAccounts(Long contactId) {
        return contactRepository.findByIdWithAccounts(contactId);
    }

    @Override
    public void saveContactIndividual(ContactDto contactIndividual, String username) {
        Contact contact;
        ContactIndividual individual;
        ContactDetails details;

        User createdBy = userRepository.findByUsername(username);

        contact = new Contact();
        contact.setContactType(ContactType.INDIVIDUAL);
        contact.setContactCategoryType(contactIndividual.getContactCategoryType());
        contact.setBestChannelToContact(contactIndividual.getBestChannelToContact());
        contact.setEngagementDate(contactIndividual.getEngagementDate());

        if (username != null) {
            contact.setCreatedBy(createdBy.getUserId());
            contact.setUpdatedBy(createdBy.getUserId());
        }

        logger.info("Saving new contact individual: " + contact);
        contactRepository.save(contact);

        if (contactIndividual.getIndividual() != null){
            individual = new ContactIndividual();
            individual.setContact(contact);
            individual.setTitle(contactIndividual.getIndividual().getTitle());
            individual.setLastName(contactIndividual.getIndividual().getLastName());
            individual.setFirstName(contactIndividual.getIndividual().getFirstName());
            individual.setMiddleName(contactIndividual.getIndividual().getMiddleName());
            individual.setSuffix(contactIndividual.getIndividual().getSuffix());
            individual.setMobileNumber(contactIndividual.getIndividual().getMobileNumber());
            individual.setEmailAddress(contactIndividual.getIndividual().getEmailAddress());
            individual.setAddress(contactIndividual.getIndividual().getAddress());
            logger.info("Saving contact individual: " + individual);
            contactIndividualRepository.save(individual);
        }

        if (contactIndividual.getAdditionalDetails() != null) {
            details = new ContactDetails();
            details.setContact(contact);
            details.setDesignationFor(contactIndividual.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactIndividual.getAdditionalDetails().getBankName());
            details.setAccountNo(contactIndividual.getAdditionalDetails().getAccountNo());
            logger.info("Saving individual other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void updateContactIndividual(ContactDto contactIndividual, String username) {
        Contact contact;
        User updatedBy = userRepository.findByUsername(username);

        // Update Contact
        if (contactIndividual.getContactId() != null) {
            contact = contactRepository.findById(contactIndividual.getContactId()).orElse(new Contact());
            contact.setContactCategoryType(contactIndividual.getContactCategoryType());
            contact.setEngagementDate(contactIndividual.getEngagementDate());
            contact.setBestChannelToContact(contactIndividual.getBestChannelToContact());

            if (username != null) {
                contact.setUpdatedBy(updatedBy.getUserId());
            }

            logger.info("Updating contact: " + contact);
            contactRepository.save(contact);

            // Track if any updates are made to the individual or details
            boolean individualUpdated = false;
            boolean detailsUpdated = false;

            // Update or create individual
            if (contactIndividual.getIndividual() != null) {
                ContactIndividual individual = contactIndividualRepository.findById(contactIndividual.getIndividual().getIndividualId())
                        .orElse(new ContactIndividual()); // Create new if not found
                individual.setContact(contact);
                individual.setTitle(contactIndividual.getIndividual().getTitle());
                individual.setLastName(contactIndividual.getIndividual().getLastName());
                individual.setFirstName(contactIndividual.getIndividual().getFirstName());
                individual.setMiddleName(contactIndividual.getIndividual().getMiddleName());
                individual.setSuffix(contactIndividual.getIndividual().getSuffix());
                individual.setMobileNumber(contactIndividual.getIndividual().getMobileNumber());
                individual.setEmailAddress(contactIndividual.getIndividual().getEmailAddress());
                individual.setAddress(contactIndividual.getIndividual().getAddress());
                logger.info("Updating contact individual: " + individual);
                contactIndividualRepository.save(individual);

                individualUpdated = true; // Track that an update was made
            }
            // Update or create additional details
            if (contactIndividual.getAdditionalDetails() != null) {
                ContactDetails details = contactDetailsRepository.findById(contactIndividual.getAdditionalDetails().getDetailId())
                        .orElse(new ContactDetails());
                details.setContact(contact);
                details.setDesignationFor(contactIndividual.getAdditionalDetails().getDesignationFor());
                details.setBankName(contactIndividual.getAdditionalDetails().getBankName());
                details.setAccountNo(contactIndividual.getAdditionalDetails().getAccountNo());
                logger.info("Updating individual details: " + details);
                contactDetailsRepository.save(details);

                detailsUpdated = true; // Track that an update was made
            }
            if (individualUpdated || detailsUpdated) {
                contact.setUpdatedBy(updatedBy.getUserId());
                contact.setUpdatedAt(new Date()); // Set the updated timestamp
                contactRepository.save(contact);
            }
        }
    }

    @Override
    public void saveContactCompany(ContactDto contactCompany, String username) {
        Contact contact;
        ContactCompany company;
        ContactDetails details;

        User createdBy = userRepository.findByUsername(username);

        contact = new Contact();
        contact.setContactType(ContactType.COMPANY);
        contact.setContactCategoryType(contactCompany.getContactCategoryType());
        contact.setBestChannelToContact(contactCompany.getBestChannelToContact());
        contact.setEngagementDate(contactCompany.getEngagementDate());

        if (createdBy != null) {
            contact.setCreatedBy(createdBy.getUserId());
            contact.setUpdatedBy(createdBy.getUserId());
        }

        logger.info("Saving new contact company: " + contact);
        contactRepository.save(contact);

        if (contactCompany.getCompany() != null) {
            company = new ContactCompany();
            company.setContact(contact);
            company.setCompanyName(contactCompany.getCompany().getCompanyName());
            company.setRegistrationType(contactCompany.getCompany().getRegistrationType());
            company.setRepresentativeName(contactCompany.getCompany().getRepresentativeName());
            company.setRepresentativeDesignation(contactCompany.getCompany().getRepresentativeDesignation());
            company.setMobileNumber(contactCompany.getCompany().getMobileNumber());
            company.setEmailAddress(contactCompany.getCompany().getEmailAddress());
            company.setAddress(contactCompany.getCompany().getAddress());
            logger.info("Saving contact company: " + company);
            contactCompanyRepository.save(company);
        }
        if (contactCompany.getAdditionalDetails() != null) {
            details = new ContactDetails();
            details.setContact(contact);
            details.setDesignationFor(contactCompany.getAdditionalDetails().getDesignationFor());
            details.setBankName(contactCompany.getAdditionalDetails().getBankName());
            details.setAccountNo(contactCompany.getAdditionalDetails().getAccountNo());
            logger.info("Saving company other details: " + details);
            contactDetailsRepository.save(details);
        }
    }

    @Override
    public void updateContactCompany(ContactDto contactCompany, String username) {
        Contact contact;
        User updatedBy = userRepository.findByUsername(username);

        if (contactCompany.getContactId() != null) {
            contact = contactRepository.findById(contactCompany.getContactId()).orElse(new Contact());
            contact.setContactCategoryType(contactCompany.getContactCategoryType());
            contact.setEngagementDate(contactCompany.getEngagementDate());
            contact.setBestChannelToContact(contactCompany.getBestChannelToContact());

            if (username != null) {
                contact.setUpdatedBy(updatedBy.getUserId());
            }

            logger.info("Updating contact: " + contact);
            contactRepository.save(contact);

            // Track if any updates are made to the company or details
            boolean companyUpdated = false;
            boolean detailsUpdated = false;

            if (contactCompany.getCompany() != null) {
                ContactCompany company = contactCompanyRepository.findById(contactCompany.getCompany().getCompanyId())
                        .orElse(new ContactCompany());
                company.setContact(contact);
                company.setCompanyName(contactCompany.getCompany().getCompanyName());
                company.setRegistrationType(contactCompany.getCompany().getRegistrationType());
                company.setRepresentativeName(contactCompany.getCompany().getRepresentativeName());
                company.setRepresentativeDesignation(contactCompany.getCompany().getRepresentativeDesignation());
                company.setMobileNumber(contactCompany.getCompany().getMobileNumber());
                company.setEmailAddress(contactCompany.getCompany().getEmailAddress());
                company.setAddress(contactCompany.getCompany().getAddress());

                logger.info("Updating contact company " + company);
                contactCompanyRepository.save(company);
                companyUpdated = true;
            }

            if (contactCompany.getAdditionalDetails() != null) {
                ContactDetails details = contactDetailsRepository.findById(contactCompany.getAdditionalDetails().getDetailId())
                        .orElse(new ContactDetails()); // Create new if not found
                details.setContact(contact);
                details.setDesignationFor(contactCompany.getAdditionalDetails().getDesignationFor());
                details.setBankName(contactCompany.getAdditionalDetails().getBankName());
                details.setAccountNo(contactCompany.getAdditionalDetails().getAccountNo());
                logger.info("Updating company details: " + details);
                contactDetailsRepository.save(details);

                detailsUpdated = true; // Track that an update was made
            }

            if (companyUpdated || detailsUpdated) {
                contact.setUpdatedBy(updatedBy.getUserId());
                contact.setUpdatedAt(new Date());
                contactRepository.save(contact);
            }
        }
    }

    @Override
    public void deleteContactById(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
