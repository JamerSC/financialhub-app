package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Cases;
import com.jamersc.springboot.financialhub.model.CaseType;
import com.jamersc.springboot.financialhub.model.Status;
import com.jamersc.springboot.financialhub.model.contact.Contact;
import com.jamersc.springboot.financialhub.service.cases.CaseService;
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

@Controller
@AllArgsConstructor
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/list-of-cases")
    public String listOfCases(Model model) {
        //List<Contact> clients = contactService.getAllContacts();
        model.addAttribute("case", new Cases());
        model.addAttribute("caseType", CaseType.values());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("status", Status.values());
        return "case/case";
    }

    @PostMapping("/add-new-case")
    public String addNewCase(@ModelAttribute("case") Cases tempCase, Model model) {
        String username = getSessionUserName();
        caseService.save(tempCase, username);
        return "redirect:/cases/list-of-cases";
    }

    @GetMapping("/case-summary")
    public String caseInformation(Model model) {
        return "case/case-summary";
    }

    private String getSessionUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
