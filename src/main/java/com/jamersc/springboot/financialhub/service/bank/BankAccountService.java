package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> getAllBankAccounts();

    BankAccount getBankAccountById(Long id);
}
