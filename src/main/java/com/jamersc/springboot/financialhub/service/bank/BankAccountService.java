package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> getAllBankAccounts();

    Page<BankAccount> findAll(Pageable pageable);

    BankAccountDto getBankAccountById(Long id);

    void saveBankAccount(BankAccountDto bankAccountDto, String username);
}
