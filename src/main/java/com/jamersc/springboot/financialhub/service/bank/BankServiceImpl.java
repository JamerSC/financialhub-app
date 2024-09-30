package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.Bank;
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
    private BankRepository bankRepo;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepo.findAll();
    }

    @Override
    public List<Bank> getAllBankAccounts() {
        return bankRepo.findAllWithAccounts();
    }

    @Override
    public Bank findBankById(Long id) {
        return bankRepo.findById(id).orElseThrow(() -> new RuntimeException("Bank Not Found"));
    }

    @Override
    public void save(Bank bank) {
        bankRepo.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepo.deleteById(id);
    }
}
