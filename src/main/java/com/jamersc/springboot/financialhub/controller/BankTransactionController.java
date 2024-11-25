package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import com.jamersc.springboot.financialhub.model.BankTransactionType;
import com.jamersc.springboot.financialhub.service.bank.BankAccountService;
import com.jamersc.springboot.financialhub.service.bank.BankService;
import com.jamersc.springboot.financialhub.service.bank.BankTransactionService;
import com.jamersc.springboot.financialhub.service.user.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/bank-transactions")
public class BankTransactionController {

    @Autowired
    private BankService bankService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankTransactionService bankTransactionService;

    @GetMapping("/deposit-accounts")
    public String depositAccounts(Model model) {
        List<Bank> listOfBankAccounts = bankService.getAllBankAccounts(); // Banks & Bank Accounts
        model.addAttribute("listOfBankAccounts", listOfBankAccounts);
        return "deposit/deposit-accounts";
    }

    @GetMapping("/{id}/deposit-transaction")
    public String depositTransaction(@PathVariable(value = "id") Long id, Model model) {
        BankAccountDto bankAccount = bankAccountService.getBankAccountById(id);
        List<BankTransaction> listOfDeposits = bankTransactionService.findTransactionsByBankAccountAndType(bankAccount.getBankAccountId(), BankTransactionType.DEPOSIT);
        BankTransactionDto deposit = new BankTransactionDto();
        deposit.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("listOfDeposits", listOfDeposits);
        model.addAttribute("deposit", deposit);
        return "deposit/deposit-transaction";
    }

    @PostMapping("/save-account-deposit")
    public String addDeposit(@ModelAttribute("deposit") BankTransactionDto deposit) {
        String username = getSessionUsername();
        bankTransactionService.processDeposit(deposit, username);
        return "redirect:/bank-transactions/deposit-transaction/" + deposit.getBankAccount().getBankAccountId();
    }

    @GetMapping("/{id}/delete-deposit-transaction")
    public String deleteDeposit(@PathVariable(value = "id") Long id) {
        BankTransactionDto bankTransaction = bankTransactionService.getTransactionById(id);
        bankTransactionService.deleteTransactionById(bankTransaction.getId());
        return "redirect:/bank-transactions/deposit-transaction/" + bankTransaction.getBankAccount().getBankAccountId();
    }

    @GetMapping("/withdrawal-accounts")
    public String withdrawalAccounts(Model model) {
        // fetch Banks & Bank Accounts
        List<Bank> listOfBankAccounts = bankService.getAllBankAccounts();
        model.addAttribute("listOfBankAccounts", listOfBankAccounts);
        return "withdrawal/withdraw-accounts";
    }

    @GetMapping("/{id}/withdrawal-transaction")
    public String accountTransaction(@PathVariable(value = "id") Long id, Model model) {
        BankAccountDto bankAccount = bankAccountService.getBankAccountById(id);
        List<BankTransaction> listOfWithdrawals = bankTransactionService.findTransactionsByBankAccountAndType(bankAccount.getBankAccountId(), BankTransactionType.WITHDRAWAL);
        BankTransactionDto withdraw = new BankTransactionDto();
        withdraw.setBankAccount(bankAccount);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("listOfWithdrawals", listOfWithdrawals);
        model.addAttribute("withdraw", withdraw);
        return "withdrawal/withdrawal-transaction";
    }

    @PostMapping("/save-account-withdrawal")
    public String addWithdrawal(@ModelAttribute("withdraw") BankTransactionDto withdraw) {
        String username = getSessionUsername();
        bankTransactionService.processWithdrawal(withdraw, username);
        return "redirect:/bank-transactions/withdrawal-transaction/" + withdraw.getBankAccount().getBankAccountId();
    }

    @GetMapping("/{id}/delete-transaction")
    public String deleteWithdrawal(@PathVariable(value = "id") Long id) {
        if (id == null) {
            // Handle the case where id is null
            throw new IllegalArgumentException("Transaction ID must not be null");
        }

        // Fetch the bank transaction by ID
        BankTransactionDto bankTransaction = bankTransactionService.getTransactionById(id);

        if (bankTransaction == null) {
            // Handle case when transaction is not found
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }

        // Delete the bank transaction
        bankTransactionService.deleteTransactionById(bankTransaction.getId());

        return "redirect:/bank-transactions/withdrawal-transaction/" + bankTransaction.getBankAccount().getBankAccountId();
    }

    public String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
