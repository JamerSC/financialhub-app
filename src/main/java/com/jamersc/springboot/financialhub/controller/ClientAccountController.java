package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.CaseAccount;
import com.jamersc.springboot.financialhub.service.client_accounts.CaseService;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
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

@Controller
@AllArgsConstructor
@RequestMapping("/client-account")
public class ClientAccountController {

    @Autowired
    private ClientAccountService clientAccountService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/list-of-cases")
    public String listOfCases(Model model) {
        model.addAttribute("cases", clientAccountService.getAllClientAccounts());
        /*model.addAttribute("case", new CaseAccount());
        model.addAttribute("caseType", CaseType.values());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("status", Status.values());*/
        return "case/case";
    }

    @PostMapping("/add-new-case")
    public String addNewCase(@ModelAttribute("case") CaseAccount tempCase, Model model) {
        String username = getSessionUserName();
        caseService.save(tempCase, username);
        return "redirect:/client-account/list-of-cases";
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
