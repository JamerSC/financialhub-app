package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.model.bank.Transaction;
import com.jamersc.springboot.financialhub.model.bank.TransactionType;
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
        List<Transaction> accountDeposits = transactionService.findTransactionsByBankAccountAndType(bankAccount.getBankAccountId(), TransactionType.DEPOSIT);
        Transaction deposit = new Transaction();
        deposit.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("accountDeposits", accountDeposits);
        model.addAttribute("deposit", deposit);
        return "deposit/deposit-transaction";
    }

    @PostMapping("/save-account-deposit")
    public String addAccountDeposit(@ModelAttribute("deposit") Transaction deposit) {
        transactionService.processDeposit(deposit);
        return "redirect:/transactions/deposit-transaction/" + deposit.getBankAccount().getBankAccountId();
    }

    @GetMapping("/delete-deposit-transaction/{id}")
    public String deleteDeposit(@PathVariable(value = "id") Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        transactionService.deleteTransactionById(transaction.getId());
        return "redirect:/transactions/deposit-transaction/" + transaction.getBankAccount().getBankAccountId();
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
        List<Transaction> accountWithdrawals = transactionService.findTransactionsByBankAccountAndType(bankAccount.getBankAccountId(), TransactionType.WITHDRAWAL);
        Transaction withdraw = new Transaction();
        withdraw.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("accountWithdrawals", accountWithdrawals);
        model.addAttribute("withdraw", withdraw);
        return "withdrawal/withdrawal-transaction";
    }

    @PostMapping("/save-account-withdrawal")
    public String addWithdrawal(@ModelAttribute("withdraw") Transaction withdraw) {
        transactionService.processWithdrawal(withdraw);
        return "redirect:/transactions/withdrawal-transaction/" + withdraw.getBankAccount().getBankAccountId();
    }
}
