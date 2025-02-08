package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import com.jamersc.springboot.financialhub.service.bank.BankTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/bank")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankService bankService;
    @Autowired
    private BankTransactionService bankTransactionService;

    @GetMapping("/banks")
    public String showBankPage(Model model,
                               @RequestParam(defaultValue = "0") int accountPage,
                               @RequestParam(defaultValue = "5") int accountSize,
                               @RequestParam(defaultValue = "0") int bankPage,
                               @RequestParam(defaultValue = "5") int bankSize) {
        // Bank Account
        Page<BankAccount> bankAccountPage = bankAccountService.findAll(PageRequest.of(accountPage,accountSize));
        List<BankAccount> listOfAllBankAccounts = bankAccountPage.getContent();
        model.addAttribute("listOfAllBankAccounts", listOfAllBankAccounts);
        model.addAttribute("totalAccountPages", bankAccountPage.getTotalPages());
        model.addAttribute("currentAccountPage", accountPage);
        model.addAttribute("accountPageSize", accountSize);

        // create/update bank account
        List<Bank> allBanks = bankService.getAllBanks();
        model.addAttribute("allBanks", allBanks);
        model.addAttribute("account", new BankAccountDto()); // replaced the new bank account entity to dto
        model.addAttribute("updateAccount", new BankAccountDto()); //

        // Banks
        Page<Bank> bankListPage = bankService.findAll(PageRequest.of(bankPage, bankSize));
        List<Bank> listOfAllBanks = bankListPage.getContent();
        model.addAttribute("totalBankPages", bankListPage.getTotalPages());
        model.addAttribute("currentBankPage", bankPage);
        model.addAttribute("bankPageSize", bankSize);
        model.addAttribute("listOfAllBanks", listOfAllBanks);

        // create/update bank
        model.addAttribute("bank", new BankDto());
        model.addAttribute("updateBank", new BankDto());
        return "bank/bank";
    }

    @PostMapping("/save-account")
    public String addBankAccount(@ModelAttribute("account") BankAccountDto accountDto) {
        String createdBy = getSessionUsername();
        bankAccountService.saveBankAccount(accountDto, createdBy);
        return "redirect:/bank/banks";
    }

    @GetMapping("/edit-bank-account")
    @ResponseBody
    public BankAccountDto editBankAccount(Long id) {
        return bankAccountService.getBankAccountById(id);
    }

    @PostMapping("/update-bank-account")
    public String updateBankAccount(@ModelAttribute("updateAccount") BankAccountDto accountDto) {
        String updatedBy = getSessionUsername();
        bankAccountService.saveBankAccount(accountDto, updatedBy);
        return "redirect:/bank/banks";
    }

    @PostMapping("/save-bank")
    public String addBankAccount(@ModelAttribute("bank") BankDto bankDto) {
        String createdBy = getSessionUsername();
        bankService.save(bankDto, createdBy);
        return "redirect:/bank/banks";
    }

    @GetMapping("/edit-bank")
    @ResponseBody
    public BankDto editBank(Long id) {
        return bankService.findBankById(id);
    }

    @PostMapping("/update-bank")
    public String updateBank(@ModelAttribute("updateBank") BankDto bankDto) {
        String updatedBy = getSessionUsername();
        bankService.save(bankDto, updatedBy);
        return "redirect:/bank/banks";
    }

    @GetMapping("/bank-statements")
    public String bankStatements(Model model) {
        List<BankAccount> listOfAllBankAccounts = bankAccountService.getAllBankAccounts();
        model.addAttribute("listOfAllBankAccounts", listOfAllBankAccounts);
        return "bank/bank-statements";
    }

    @GetMapping("/bank-account-journal/{id}")
    public String bankAccountJournal(@PathVariable(value = "id") Long accountId, Model model) {
        BankAccountDto account = bankAccountService.getBankAccountById(accountId);
        List<BankTransaction> bankTransactions = bankTransactionService.findBankAccountById(account.getBankAccountId());
        model.addAttribute("account", account); // bank account by id
        model.addAttribute("transactions", bankTransactions);
        return "bank/bank-account-journal";
    }

    public String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
