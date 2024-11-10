package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.CaseType;
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
    public ClientAccountDto findCaseAccountById(Long id) {
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
        model.addAttribute("caseSummary", caseSummary);
        return "case/case-summary";
    }


    /* ****    PROJECT ACCOUNT **** */

    @GetMapping("/list-of-projects")
    public String listOfProjects(Model model) {
        model.addAttribute("listOfProjects", clientAccountService.getAllProjectAccounts());
        model.addAttribute("projectAccount", new ClientAccountDto());
         model.addAttribute("updateProjectAccount", new ClientAccountDto());
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

    @GetMapping("/edit-transfer-of-title-account")
    @ResponseBody
    public ClientAccountDto findTransferOfTitleAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-transfer-of-title-account")
    public String updateTransferOfTitleAccount(@ModelAttribute("updateProjectAccount")
                                            ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientTransferOfTitleAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-settlement-of-estate-account")
    public String addSettlementOfEstateAccount(@ModelAttribute("projectAccount")
                                                   ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSettlementOfEstateAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-settlement-of-estate-account")
    @ResponseBody
    public ClientAccountDto findSettlementOfEstateAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-settlement-of-estate-account")
    public String updateSettlementOfEstateAccount(@ModelAttribute("updateProjectAccount")
                                                  ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientSettlementOfEstateAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-annotation-account")
    public String addTitleAnnotationAccount(@ModelAttribute("projectAccount")
                                                ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleAnnotationAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-title-annotation-account")
    @ResponseBody
    public ClientAccountDto findTitleAnnotationAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-title-annotation-account")
    public String updateTitleAnnotationAccount(@ModelAttribute("updateProjectAccount")
                                                  ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientTitleAnnotationAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-title-other-account")
    public String addTitleOtherAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientTitleOtherAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-title-other-account")
    @ResponseBody
    public ClientAccountDto findTitleOtherAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-title-other-account")
    public String updateTitleOtherAccount(@ModelAttribute("updateProjectAccount")
                                               ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientTitleOtherAccount(updateProjectAccount, updatedBy);
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

    @GetMapping("/edit-business-registration-account")
    @ResponseBody
    public ClientAccountDto findBusinessRegistrationAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-business-registration-account")
    public String updateBusinessRegistrationAccount(@ModelAttribute("updateProjectAccount")
                                          ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientBusinessRegistrationAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-renewal-account")
    public String addBusinessRenewalAccount(@ModelAttribute("projectAccount")
                                                 ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessRenewalAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-business-renewal-account")
    @ResponseBody
    public ClientAccountDto findBusinessRenewalAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-business-renewal-account")
    public String updateBusinessRenewalAccount(@ModelAttribute("updateProjectAccount")
                                                    ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientBusinessRenewalAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-closure-account")
    public String addBusinessClosureAccount(@ModelAttribute("projectAccount")
                                                 ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessClosureAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-business-closure-account")
    @ResponseBody
    public ClientAccountDto findBusinessClosureAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-business-closure-account")
    public String updateBusinessClosureAccount(@ModelAttribute("updateProjectAccount")
                                               ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientBusinessClosureAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-business-other-account")
    public String addBusinessOtherAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientBusinessOtherAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-business-other-account")
    @ResponseBody
    public ClientAccountDto findBusinessOtherAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-business-other-account")
    public String updateBusinessOtherAccount(@ModelAttribute("updateProjectAccount")
                                               ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientBusinessOtherAccount(updateProjectAccount, updatedBy);
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

    @GetMapping("/edit-sec-registration-account")
    @ResponseBody
    public ClientAccountDto findSecRegistrationAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-sec-registration-account")
    public String updateSecRegistrationAccount(@ModelAttribute("updateProjectAccount")
                                             ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientSecRegistrationAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-sec-amendment-account")
    public String addSecAmendmentAccount(@ModelAttribute("projectAccount")
                                            ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSecAmendmentAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-sec-amendment-account")
    @ResponseBody
    public ClientAccountDto findSecAmendmentAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-sec-amendment-account")
    public String updateSecAmendmentAccount(@ModelAttribute("updateProjectAccount")
                                               ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientSecAmendmentAccount(updateProjectAccount, updatedBy);
        return "redirect:/client-account/list-of-projects";
    }

    @PostMapping("/add-sec-stock-increase-account")
    public String addSecStockIncreaseAccount(@ModelAttribute("projectAccount")
                                         ClientAccountDto projectAccount) {
        String createdBy = getSessionUserName();
        clientAccountService.saveClientSecStockIncreaseAccount(projectAccount, createdBy);
        return "redirect:/client-account/list-of-projects";
    }

    @GetMapping("/edit-sec-stock-increase-account")
    @ResponseBody
    public ClientAccountDto findSecStockIncreaseAccountById(Long id) {
        return clientAccountService.getClientAccountById(id);
    }

    @PostMapping("/update-sec-stock-increase-account")
    public String updateSecStockIncreaseAccount(@ModelAttribute("updateProjectAccount")
                                            ClientAccountDto updateProjectAccount) {
        String updatedBy = getSessionUserName();
        clientAccountService.updateClientSecStockIncreaseAccount(updateProjectAccount, updatedBy);
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
    public ClientAccountDto findRetainerAccountInformationById(Long id) {
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
