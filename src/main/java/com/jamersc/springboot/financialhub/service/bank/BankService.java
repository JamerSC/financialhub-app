package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankService {

    List<Bank> getAllBanks();

    Page<Bank> findAll(Pageable pageable);

    List<Bank> getAllBankAccounts();

    Bank findBankById(Long id);

    void save(Bank bank, String username);

    void update(Bank bank, String username);

    void deleteBankById(Long id);
}
