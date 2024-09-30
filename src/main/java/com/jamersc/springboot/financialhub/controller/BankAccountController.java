package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.model.bank.Transaction;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import com.jamersc.springboot.financialhub.service.bank.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TransactionService transactionService;

    @GetMapping("/banks")
    public String showBankPage(Model model) {
        List<Bank> listOfAllBanks = bankService.getAllBanks();
        List<BankAccount> listOfAllBankAccounts = bankAccountService.getAllBankAccounts();
        BankAccount account = new BankAccount();
        Bank bank = new Bank();
        model.addAttribute("listOfAllBanks", listOfAllBanks);
        model.addAttribute("listOfAllBankAccounts", listOfAllBankAccounts);
        model.addAttribute("account", account);
        model.addAttribute("bank", bank);
        return "bank/bank";
    }

    @PostMapping("/add-account")
    public String addBankAccount(@ModelAttribute("account") BankAccount account) {
        bankAccountService.save(account);
        return "redirect:/bank/banks";
    }

    @PostMapping("/add-bank")
    public String addBankAccount(@ModelAttribute("bank") Bank bank) {
        bankService.save(bank);
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
        BankAccount account = bankAccountService.getBankAccountById(accountId);
        List<Transaction> transactions = transactionService.findBankAccountById(account.getId());
        model.addAttribute("account", account); // bank account by id
        model.addAttribute("transactions", transactions);
        return "bank/bank-account-journal";
    }
}
