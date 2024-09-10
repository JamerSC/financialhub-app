package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.BankDeposit;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankDepositService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/deposits")
public class DepositController {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankDepositService bankDepositService;

    @GetMapping("/deposit")
    public String depositPage(Model model) {
        // fetch Banks & Bank Accounts
        List<Bank> listOfBankAccounts = bankService.getAllBankAccounts();
        model.addAttribute("listOfBankAccounts", listOfBankAccounts);
        return "deposit/deposit";
    }

    @GetMapping("/account-deposit/{id}")
    public String accountDeposit(@PathVariable(value = "id") Long id, Model model) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        List<BankDeposit> bankDeposits = bankDepositService.findBankAccountById(bankAccount.getId());
        BankDeposit newDeposit = new BankDeposit();
        newDeposit.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("bankDeposits", bankDeposits);
        model.addAttribute("newDeposit", newDeposit);
        return "deposit/account-deposit";
    }

    @PostMapping("/add-deposit")
    public String addDeposit(@ModelAttribute("newDeposit") BankDeposit bankDeposit) {
        bankDepositService.save(bankDeposit);
        return "redirect:/deposits/account-deposit/" + bankDeposit.getBankAccount().getId();
    }

    @GetMapping("/delete-deposit/{id}")
    public String deleteDeposit(@PathVariable(value = "id") Long id) {
        BankDeposit bankDeposit = bankDepositService.getBankDepositById(id);
        bankDepositService.deleteBankDepositById(bankDeposit.getId());
        return "redirect:/deposits/account-deposit/" + bankDeposit.getBankAccount().getId();
    }
}
