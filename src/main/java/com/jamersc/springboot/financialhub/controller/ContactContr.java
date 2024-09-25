package com.jamersc.springboot.financialhub.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class ContactContr {

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

    @PostMapping("/save-contact-company")
    public String addContactCompany(@ModelAttribute("contactCompany") Contact contactCompany) {
        String createdBy = getSessionUsername();
        contactService.saveContactIndividual(contactCompany, createdBy);
        return "redirect:/contacts/contact-list";
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
