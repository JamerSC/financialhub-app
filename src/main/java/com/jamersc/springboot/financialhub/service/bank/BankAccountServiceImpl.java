package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank Account not found."));
    }

    @Override
    public void save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
}
