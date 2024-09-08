package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.BankDeposit;

import java.util.List;

public interface BankDepositService {

    List<BankDeposit> getAllBankDeposits();

    BankDeposit getBankDepositById(Long id);

    List<BankDeposit> findBankAccountById(Long id);

    void save(BankDeposit bankDeposit);

    void deleteBankDepositById(Long id);
}
