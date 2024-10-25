package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.model.CaseType;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Status;
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
        model.addAttribute("accountCases", clientAccountService.getAllCaseAccounts());
        model.addAttribute("caseAccount", new ClientAccountDto());
        model.addAttribute("updateCaseAccount", new ClientAccountDto());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("caseType", CaseType.values());
        model.addAttribute("status", Status.values());
        return "case/case";
    }

    @PostMapping("/add-case-account")
    public String addNewCase(@ModelAttribute("account") ClientAccountDto caseAccount) {
        String username = getSessionUserName();
        clientAccountService.saveClientCaseAccount(caseAccount, username);
        return "redirect:/client-account/list-of-cases";
    }

    @GetMapping("/edit-case-account")
    @ResponseBody
    public ClientAccountDto editCaseAccount(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-case-account")
    public String updateCaseAccount(@ModelAttribute("updateCaseAccount") ClientAccountDto caseAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientCaseAccount(caseAccount, updatedBy);
        return "redirect:/client-account/list-of-cases";
    }

    @GetMapping("/{id}/case-summary")
    public String caseInformation(@PathVariable(value = "id") Long id,Model model) {
        ClientAccountDto caseSummary = clientAccountService.getClientAccountById(id);
        //model.addAttribute("caseSummary", ClientAccountMapper.toClientAccountEntity(caseSummary));
        model.addAttribute("caseSummary", caseSummary);
        return "case/case-summary";
    }

    @GetMapping("/list-of-retainers")
    public String listOfRetainers(Model model) {
        model.addAttribute("accountRetainers", clientAccountService.getAllRetainerAccounts());
        return "retainer/retainer";
    }

    @GetMapping("/retainer-activity")
    public String retainerActivity(Model model) {
        return "retainer/retainer-activity";
    }

    @GetMapping("/list-of-projects")
    public String listOfProjects(Model model) {
        return "project/project";
    }

    @GetMapping("/project-summary")
    public String projectSummary(Model model) {
        return "project/project-summary";
    }

    private String getSessionUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
