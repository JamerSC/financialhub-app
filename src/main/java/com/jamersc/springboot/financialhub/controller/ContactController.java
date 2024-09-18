package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.ContactSubType;
import com.jamersc.springboot.financialhub.model.ContactType;
import com.jamersc.springboot.financialhub.model.Sample;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import com.jamersc.springboot.financialhub.service.contact.ContactSubTypeService;
import com.jamersc.springboot.financialhub.service.contact.ContactTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private ContactSubTypeService contactSubTypeService;

    @GetMapping("/contact-list")
    public String payeeList(Model model) {
        List<ContactType> contactTypeList = contactTypeService.getAllContactTypes();
        List<ContactSubType> contactSubTypeList = contactSubTypeService.getAllContactSubTypes();
        List<Contact> listOfContacts = contactService.getAllContacts();
        Contact contact = new Contact();
        model.addAttribute("contactTypeList", contactTypeList);
        model.addAttribute("contactSubTypeList", contactSubTypeList);
        model.addAttribute("listOfContacts", listOfContacts);
        model.addAttribute("contact", contact);
        return "contact/contact";
    }

    @PostMapping("/add-contact")
    public String addContact(@ModelAttribute("contact") Contact contact) {
        contactService.save(contact);
        return "redirect:/contacts/contact-list";
    }
}
