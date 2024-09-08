package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/bank")
public class BankSettingsController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankService bankService;

    @GetMapping("/banks")
    public String showBankPage(Model model) {
        List<Bank> listOfAllBanks = bankService.getAllBanks();
        List<BankAccount> listOfAllBankAccounts = bankAccountService.getAllBankAccounts();
        model.addAttribute("listOfAllBanks", listOfAllBanks);
        model.addAttribute("listOfAllBankAccounts", listOfAllBankAccounts);
        return "settings/bank";
    }
}
