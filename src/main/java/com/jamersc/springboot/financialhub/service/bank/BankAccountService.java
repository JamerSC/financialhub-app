package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> getAllBankAccounts();

    Page<BankAccount> findAll(Pageable pageable);

    BankAccount getBankAccountById(Long id);

    void saveBankAccount(BankAccount bankAccount, String username);
}
