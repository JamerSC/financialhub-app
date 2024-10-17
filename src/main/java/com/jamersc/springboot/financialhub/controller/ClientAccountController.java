package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.CaseType;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Status;
import com.jamersc.springboot.financialhub.service.client_accounts.CaseService;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/client-account")
public class ClientAccountController {

    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/list-of-cases")
    public String listOfCases(Model model) {
        model.addAttribute("accountCases", clientAccountService.getAllClientAccounts());
        model.addAttribute("account", new ClientAccountDto());
        model.addAttribute("updateAccount", new ClientAccountDto());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("caseType", CaseType.values());
        model.addAttribute("status", Status.values());
        return "case/case";
    }

    @PostMapping("/add-case-account")
    public String addNewCase(@ModelAttribute("account") ClientAccountDto caseAccount, Model model) {
        String username = getSessionUserName();
        clientAccountService.saveClientAccount(caseAccount, username);
        return "redirect:/client-account/list-of-cases";
    }

    @GetMapping("/edit-case-account")
    @ResponseBody
    public ClientAccountDto editCaseAccount(Long id) {
        return clientAccountService.getClientAccountById(id);
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
