package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.Transaction;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import com.jamersc.springboot.financialhub.service.bank.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/transactions")
public class BankTransactionController {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/deposit-accounts")
    public String depositAccounts(Model model) {
        // fetch Banks & Bank Accounts
        List<Bank> listOfBankAccounts = bankService.getAllBankAccounts();
        model.addAttribute("listOfBankAccounts", listOfBankAccounts);
        return "deposit/deposit-accounts";
    }

    @GetMapping("/deposit-transaction/{id}")
    public String depositTransaction(@PathVariable(value = "id") Long id, Model model) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        List<Transaction> ListOfAccountTransaction = transactionService.findBankAccountById(bankAccount.getId());
        //Transaction newTransaction = new Transaction();
        //newTransaction.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("transactions", ListOfAccountTransaction);
        //model.addAttribute("newTransaction", newTransaction);
        return "deposit/deposit-transaction";
    }

    /*@PostMapping("/add-deposit")
    public String addDeposit(@ModelAttribute("newDeposit") BankDeposit bankDeposit) {
        bankDepositService.save(bankDeposit);
        return "redirect:/deposits/account-deposit/" + bankDeposit.getBankAccount().getId();
    }*/

    @GetMapping("/delete-deposit-transaction/{id}")
    public String deleteDeposit(@PathVariable(value = "id") Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        transactionService.deleteTransactionById(transaction.getId());
        return "redirect:/transactions/deposit-transaction/" + transaction.getBankAccount().getId();
    }

    @GetMapping("/withdrawal-accounts")
    public String withdrawalAccounts(Model model) {
        // fetch Banks & Bank Accounts
        List<Bank> listOfBankAccounts = bankService.getAllBankAccounts();
        model.addAttribute("listOfBankAccounts", listOfBankAccounts);
        return "withdrawal/withdraw-accounts";
    }

    @GetMapping("/withdrawal-transaction/{id}")
    public String accountTransaction(@PathVariable(value = "id") Long id, Model model) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(id);
        List<Transaction> ListOfAccountTransaction = transactionService.findBankAccountById(bankAccount.getId());
        //Transaction newTransaction = new Transaction();
        //newTransaction.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("transactions", ListOfAccountTransaction);
        //model.addAttribute("newTransaction", newTransaction);
        return "withdrawal/withdrawal-transaction";
    }
}
