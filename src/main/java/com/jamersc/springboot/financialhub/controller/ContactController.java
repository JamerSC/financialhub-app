package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.ContactCategory;
import com.jamersc.springboot.financialhub.model.ContactSubCategory;
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
        List<ContactCategory> contactCategoryList = contactTypeService.getAllContactTypes();
        List<ContactSubCategory> contactSubCategoryList = contactSubTypeService.getAllContactSubTypes();
        List<Contact> listOfContacts = contactService.getAllContacts();
        Contact contact = new Contact();
        model.addAttribute("contactCategoryList", contactCategoryList);
        model.addAttribute("contactSubCategoryList", contactSubCategoryList);
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
