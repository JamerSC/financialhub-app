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
        model.addAttribute("projectAccount", new ClientAccountDto());
        /* model.addAttribute("updateProjectAccount", new ClientAccountDto()); */
        model.addAttribute("clients", contactService.getAllContacts());
        model.addAttribute("status", Status.values());
        return "project/project";
    }

    /* ** Property Account ** */

    @PostMapping("/add-transfer-of-title-account")
    public String addTransferOfTitleAccount(@ModelAttribute("projectAccount")
                                                ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTransferOfTitleAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-settlement-of-estate-account")
    public String addSettlementOfEstateAccount(@ModelAttribute("projectAccount")
                                                   ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSettlementOfEstateAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-annotation-account")
    public String addTitleAnnotationAccount(@ModelAttribute("projectAccount")
                                                ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleAnnotationAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-other-account")
    public String addTitleOtherAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleOtherAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    /* **  Business Account ** */

    @PostMapping("/add-business-registration-account")
    public String addBusinessRegistrationAccount(@ModelAttribute("projectAccount")
                                       ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessRegistrationAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-renewal-account")
    public String addBusinessRenewalAccount(@ModelAttribute("projectAccount")
                                                 ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessRenewalAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-closure-account")
    public String addBusinessClosureAccount(@ModelAttribute("projectAccount")
                                                 ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessClosureAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-other-account")
    public String addBusinessOtherAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessOtherAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    /* ** SEC Account ** */

    @PostMapping("/add-sec-registration-account")
    public String addSecRegistrationAccount(@ModelAttribute("projectAccount")
                                          ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSecRegistrationAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-sec-amendment-account")
    public String addSecAmendmentAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSecAmendmentAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-sec-stock-increase-account")
    public String addSecStockIncreaseAccount(@ModelAttribute("projectAccount")
                                         ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSecStockIncreaseAccount(projectAccount, createdBy);
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
