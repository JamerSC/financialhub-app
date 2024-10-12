package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.model.bank.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankService {

    List<Bank> getAllBanks();

    Page<Bank> findAll(Pageable pageable);

    List<Bank> getAllBankAccounts();

    BankDto findBankById(Long id);

    void save(BankDto bankDto, String username);

    void deleteBankById(Long id);
}

