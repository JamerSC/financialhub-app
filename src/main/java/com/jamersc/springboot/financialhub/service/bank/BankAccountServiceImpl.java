package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
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
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount getBankAccountById(Long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank Account not found."));
    }

    @Override
    public void saveBankAccount(BankAccount bankAccount, String username) {
        BankAccount tempBankAccount;
        if (bankAccount.getBankAccountId() != null) {
            tempBankAccount = bankAccountRepository.findById(bankAccount.getBankAccountId()).orElse(new BankAccount());
            tempBankAccount.setAccountHolderName(bankAccount.getAccountHolderName());
            tempBankAccount.setAccountNumber(bankAccount.getAccountNumber());
            if (bankAccount.getBank() != null && bankAccount.getBank().getBankId() != null) {
                Bank bankId = bankRepository.getReferenceById(bankAccount.getBank().getBankId());
                tempBankAccount.setBank(bankId);
            }
            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                tempBankAccount.setUpdatedBy(updatedBy.getId());
            }
        } else {
            tempBankAccount = new BankAccount();
            User createdBy = userRepository.findByUsername(username);
            if (createdBy != null) {
                tempBankAccount.setCreatedBy(createdBy.getId());
                tempBankAccount.setUpdatedBy(createdBy.getId());
            }
            if (bankAccount.getBank() != null && bankAccount.getBank().getBankId() != null) {
                Bank bankId = bankRepository.getReferenceById(bankAccount.getBank().getBankId());
                tempBankAccount.setBank(bankId);
            }
            tempBankAccount.setAccountHolderName(bankAccount.getAccountHolderName());
            tempBankAccount.setAccountNumber(bankAccount.getAccountNumber());
            Double defaultBankAccountBalance = 0.00;
            tempBankAccount.setAccountBalance(defaultBankAccountBalance);
        }
        bankAccountRepository.save(tempBankAccount);
    }
}
