package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.BankDeposit;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.repository.BankDepositRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankDepositServiceImpl implements BankDepositService {

    @Autowired
    private BankDepositRepository bankDepositRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<BankDeposit> getAllBankDeposits() {
        return bankDepositRepository.findAll();
    }

    @Override
    public BankDeposit getBankDepositById(Long id) {
        return bankDepositRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank deposit not found."));
    }

    @Override
    public List<BankDeposit> findBankAccountById(Long id) {
        return bankDepositRepository.findByBankAccount_Id(id);
    }

    @Override
    public void save(BankDeposit bankDeposit) {
        if (bankDeposit.getBankAccount() != null && bankDeposit.getBankAccount().getId() != null) {
            BankAccount bankAccount = bankAccountRepository.getReferenceById(bankDeposit.getBankAccount().getId());
            bankDeposit.setBankAccount(bankAccount);
        }
        bankDepositRepository.save(bankDeposit);
    }

    @Override
    public void deleteBankDepositById(Long id) {
        bankDepositRepository.deleteById(id);
    }
}
