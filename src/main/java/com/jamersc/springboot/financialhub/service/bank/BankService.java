package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.Bank;

import java.util.List;

public interface BankService {

    List<Bank> getAllBanks();

    List<Bank> getAllBankAccounts();
    Bank findBankById(Long id);

    void save(Bank bank);

    void deleteBankById(Long id);
}
