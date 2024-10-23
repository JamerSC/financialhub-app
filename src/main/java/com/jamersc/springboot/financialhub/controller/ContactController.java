package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.ContactCategoryType;
import com.jamersc.springboot.financialhub.model.RegistrationType;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact-list")
    public String payeeList(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contactIndividual", new ContactDto());
        model.addAttribute("updateContactIndividual", new ContactDto());
        model.addAttribute("contactCompany", new ContactDto());
        model.addAttribute("updateContactCompany", new ContactDto());
        model.addAttribute("contactCategoryType", ContactCategoryType.values());
        model.addAttribute("registrationType", RegistrationType.values());
        model.addAttribute("contacts", contacts);
        return "contact/contact";
    }

    @PostMapping("/save-contact-individual")
    public String addContactIndividual(@ModelAttribute("contactIndividual") ContactDto contactIndividual) {
        String createdBy = getSessionUsername();
        contactService.saveContactIndividual(contactIndividual, createdBy);
        return "redirect:/contacts/contact-list";
    }

    // edit individual contact
    @GetMapping("/{id}/edit-individual-contact")
    @ResponseBody
    public ContactDto findIndividualContact(@PathVariable(value = "id") Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/update-contact-individual")
    public String updateContactIndividual(@ModelAttribute("updateContactIndividual") ContactDto contactIndividual) {
        String updatedBy = getSessionUsername();
        contactService.updateContactIndividual(contactIndividual, updatedBy);
        return "redirect:/contacts/contact-list";
    }

    @PostMapping("/save-contact-company")
    public String addContactCompany(@ModelAttribute("contactCompany") ContactDto contactCompany) {
        String createdBy = getSessionUsername();
        contactService.saveContactCompany(contactCompany, createdBy);
        return "redirect:/contacts/contact-list";
    }

    // edit individual contact
    @GetMapping("/{id}/edit-company-contact")
    @ResponseBody
    public ContactDto findCompanyContact(@PathVariable(value = "id") Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/update-contact-company")
    public String updateContactCompany(@ModelAttribute("updateContactCompany") ContactDto contactCompany) {
        String updatedBy = getSessionUsername();
        contactService.updateContactCompany(contactCompany, updatedBy);
        return "redirect:/contacts/contact-list";
    }

    @GetMapping("/{id}/contact-info")
    public String getContactInformation(@PathVariable(value = "id") Long id, Model model) {
        Contact contact = contactService.findByIdWithAccounts(id);
        model.addAttribute("contact", contact);
        return "contact/contact-info";
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
