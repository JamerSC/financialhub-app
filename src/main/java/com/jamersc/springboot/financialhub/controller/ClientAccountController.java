package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/client-account")
public class ClientAccountController {

    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private ContactService contactService;


    /*  ****  CASE ACCOUNT  **** */

    @GetMapping("/list-of-cases")
    public String listOfCases(Model model) {
        model.addAttribute("listOfCases", clientAccountService.getAllCaseAccounts());
        model.addAttribute("caseAccount", new ClientAccountDto());
        model.addAttribute("updateCaseAccount", new ClientAccountDto());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("caseType", CaseType.values());
        model.addAttribute("status", Status.values());
        return "case/case";
    }

    @PostMapping("/add-case-account")
    public String addCaseAccount(@ModelAttribute("account") ClientAccountDto caseAccount) {
        String username = getSessionUserName();
        clientAccountService.saveClientCaseAccount(caseAccount, username);
        return "redirect:/client-account/list-of-cases";
    }

    @GetMapping("/edit-case-account")
    @ResponseBody
    public ClientAccountDto findCaseAccount(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-case-account")
    public String updateCaseAccount(@ModelAttribute("updateCaseAccount") ClientAccountDto caseAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientCaseAccount(caseAccount, updatedBy);
        return "redirect:/client-account/list-of-cases";
    }

    @GetMapping("/{id}/case-summary")
    public String viewCaseInformation(@PathVariable(value = "id") Long id, Model model) {
        ClientAccountDto caseSummary = clientAccountService.getClientAccountById(id);
        //model.addAttribute("caseSummary", ClientAccountMapper.toClientAccountEntity(caseSummary));
        model.addAttribute("caseSummary", caseSummary);
        return "case/case-summary";
    }


    /* ****    PROJECT ACCOUNT **** */

    @GetMapping("/list-of-projects")
    public String listOfProjects(Model model) {
        model.addAttribute("listOfProjects", clientAccountService.getAllProjectAccounts());
        model.addAttribute("transferOfTitleAccount", new ClientAccountDto());
        model.addAttribute("settlementOfEstateAccount", new ClientAccountDto());
        model.addAttribute("titleAnnotationAccount", new ClientAccountDto());
        model.addAttribute("titleOtherAccount", new ClientAccountDto());
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("status", Status.values());
        return "project/project";
    }

    @PostMapping("/add-transfer-of-title-account")
    public String addTransferOfTitleAccount(@ModelAttribute("transferOfTitleAccount")
                                                ClientAccountDto transferOfTitleAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTransferOfTitleAccount(transferOfTitleAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-settlement-of-estate-account")
    public String addSettlementOfEstateAccount(@ModelAttribute("settlementOfEstateAccount")
                                                   ClientAccountDto settlementOfEstateAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSettlementOfEstateAccount(settlementOfEstateAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-annotation-account")
    public String addTitleAnnotationAccount(@ModelAttribute("titleAnnotationAccount")
                                                ClientAccountDto titleAnnotationAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleAnnotationAccount(titleAnnotationAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-other-account")
    public String addTitleOtherAccount(@ModelAttribute("titleOtherAccount")
                                            ClientAccountDto titleOtherAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleOtherAccount(titleOtherAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/{id}/project-summary")
    public String projectSummary(@PathVariable(value = "id") Long id, Model model) {
        ClientAccountDto projectSummary = clientAccountService.getClientAccountById(id);
        model.addAttribute("projectSummary", projectSummary);
        return "project/project-summary";
    }


    /*  ****  RETAINER ACCOUNT  **** */

    @GetMapping("/list-of-retainers")
    public String listOfRetainers(Model model) {
        model.addAttribute("listOfRetainers", clientAccountService.getAllRetainerAccounts());
        model.addAttribute("retainerAccount", new ClientAccountDto());
        model.addAttribute("updateRetainerAccount", new ClientAccountDto());
        List<Long> clientsWithRetainers = clientAccountService.getClientsWithRetainers();
        model.addAttribute("clientsWithRetainers", clientsWithRetainers);
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("status", Status.values());
        return "retainer/retainer";
    }

    @PostMapping("/add-retainer-account")
    public String addRetainerAccount(@ModelAttribute("retainerAccount") ClientAccountDto clientAccountDto) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientRetainerAccount(clientAccountDto, createdBy);
        return "redirect:/client-account/list-of-retainers";
    }

    @GetMapping("/edit-retainer-account")
    @ResponseBody
    public ClientAccountDto findRetainerAccountInformation(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-retainer-account")
    public String updateRetainerAccount(@ModelAttribute("updateRetainerAccount") ClientAccountDto clientAccountDto) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientRetainerAccount(clientAccountDto, updatedBy);
        return "redirect:/client-account/list-of-retainers";
    }

    @GetMapping("/{id}/retainer-activity")
    public String retainerActivity(@PathVariable(value = "id") Long id, Model model) {
        ClientAccountDto retainer = clientAccountService.getClientAccountById(id);
        model.addAttribute("retainer", retainer);
        return "retainer/retainer-activity";
    }

    private String getSessionUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
