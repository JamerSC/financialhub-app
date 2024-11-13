package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankAccountDto;
import com.jamersc.springboot.financialhub.mapper.BankAccountMapper;
import com.jamersc.springboot.financialhub.mapper.BankMapper;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.repository.BankRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankAccountServiceImpl implements BankAccountService{

    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
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
    public Page<BankAccount> findAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable);
    }

    @Override
    public BankAccountDto getBankAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount != null) {
            // Mapper static method no need for autowired
            BankAccountDto bankAccountDto = BankAccountMapper.toBankAccountDto(bankAccount);
            logger.info("Find Bank account details: " + bankAccountDto);
            return bankAccountDto;
        }
        throw new RuntimeException("Bank Account ID not found!");
    }

    @Override
    public void saveBankAccount(BankAccountDto bankAccountDto, String username) {
        BankAccount bankAccount;
        if (bankAccountDto.getBankAccountId() != null) {
            bankAccount = bankAccountRepository.findById(bankAccountDto.getBankAccountId()).orElse(new BankAccount());
            bankAccount.setAccountHolderName(bankAccountDto.getAccountHolderName());
            bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
            if (bankAccountDto.getBank() != null) {
                // Mapper static method no need for autowired
                Bank bank = BankMapper.toBankEntity(bankAccountDto.getBank());
                Bank bankId = bankRepository.findById(bank.getBankId()).orElse(null);
                bankAccount.setBank(bankId);
            }
            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                bankAccount.setUpdatedBy(updatedBy.getUserId());
            }
            logger.info("Updated Bank ID No. " + bankAccountDto.getBankAccountId());
        } else {
            bankAccount = new BankAccount();
            User createdBy = userRepository.findByUsername(username);
            if (createdBy != null) {
                bankAccount.setCreatedBy(createdBy.getUserId());
                bankAccount.setUpdatedBy(createdBy.getUserId());
            }
            // Mapper static method no need for autowired
            Bank bankId = BankMapper.toBankEntity(bankAccountDto.getBank());
            bankAccount.setBank(bankId);
            bankAccount.setAccountHolderName(bankAccountDto.getAccountHolderName());
            bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
            Double defaultBankAccountBalance = 0.00;
            bankAccount.setAccountBalance(defaultBankAccountBalance);
            logger.info("New bank account created successfully!!");
        }
        bankAccountRepository.save(bankAccount);
    }
}
