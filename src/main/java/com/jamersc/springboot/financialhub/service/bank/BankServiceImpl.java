package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.repository.BankRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
    public void save(Bank bank, String username) {
        Bank tempBank = new Bank();
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            tempBank.setCreatedBy(createdBy.getId());
            tempBank.setUpdatedBy(createdBy.getId());
        }
        tempBank.setName(bank.getName());
        tempBank.setAbbreviation(bank.getAbbreviation());
        tempBank.setBranch(bank.getBranch());
        bankRepository.save(tempBank);
    }

    @Override
    public void update(Bank bank, String username) {
        // Debugging: Print the bankId to see if it is null
        System.out.println("Updating bank with ID: " + bank.getBankId());

        // Ensure bankId is not null
        if (bank.getBankId() == null) {
            throw new IllegalArgumentException("Bank ID must not be null.");
        }

        Bank tempBank = bankRepository.findById(bank.getBankId())
                .orElseThrow(() -> new RuntimeException("Bank ID not found."));
        User updatedBy = userRepository.findByUsername(username);
        if (tempBank != null) {
            tempBank.setUpdatedBy(updatedBy.getId());
            tempBank.setBankId(bank.getBankId());
            tempBank.setName(bank.getName());
            tempBank.setAbbreviation(bank.getAbbreviation());
            tempBank.setBranch(bank.getBranch());
            bankRepository.save(tempBank);
        }
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }
}
