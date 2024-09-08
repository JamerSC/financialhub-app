package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.repository.BankRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public List<Bank> getAllBankAccounts() {
        return bankRepository.findAllWithAccounts();
    }

    @Override
    public Bank findBankById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank Not Found"));
    }

    @Override
    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }
}
