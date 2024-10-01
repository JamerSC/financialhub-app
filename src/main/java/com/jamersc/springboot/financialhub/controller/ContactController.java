package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.contact.Contact;
import com.jamersc.springboot.financialhub.model.contact.ContactCategoryType;
import com.jamersc.springboot.financialhub.model.contact.RegistrationType;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact-list")
    public String payeeList(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contactIndividual", new Contact());
        model.addAttribute("contactCompany", new Contact());
        model.addAttribute("contactCategoryType", ContactCategoryType.values());
        model.addAttribute("registrationType", RegistrationType.values());
        model.addAttribute("contacts", contacts);
        return "contact/contact";
    }

    @PostMapping("/save-contact-individual")
    public String addContactIndividual(@ModelAttribute("contactIndividual") Contact contactIndividual) {
        String createdBy = getSessionUsername();
        contactService.saveContactIndividual(contactIndividual, createdBy);
        return "redirect:/contacts/contact-list";
    }

    @GetMapping("/edit-contact-individual")
    @ResponseBody
    public Contact updateContactIndividual(Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/save-contact-company")
    public String addContactCompany(@ModelAttribute("contactCompany") Contact contactCompany) {
        String createdBy = getSessionUsername();
        contactService.saveContactCompany(contactCompany, createdBy);
        return "redirect:/contacts/contact-list";
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
