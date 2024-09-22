package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("contacts", contacts);
        return "contact/contact";
    }

    @PostMapping("/add-contact")
    public String addContact(@ModelAttribute("contact") Contact contact) {
        contactService.save(contact);
        return "redirect:/contacts/contact-list";
    }
}
