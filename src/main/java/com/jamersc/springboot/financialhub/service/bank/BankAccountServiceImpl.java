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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankAccountServiceImpl implements BankAccountService{

    //private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private BankAccountMapper bankAccountMapper;

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
            //logger.info("Find Bank account details: " + dto);
            return bankAccountMapper.toBankAccountDto(bankAccount);
        }
        throw new RuntimeException("Bank Account ID not found!");
    }

    @Override
    public void saveBankAccount(BankAccountDto bankAccountDto, String username) {
        BankAccount bankAccount;

        if (bankAccountDto.getBankAccountId() != null) {
            bankAccount = bankAccountRepository.findById(bankAccountDto.getBankAccountId()).orElse(new BankAccount());
        } else {
            bankAccount = new BankAccount();
            bankAccount.setAccountBalance(0.00); // Default balance
        }

        // Set Bank
        if (bankAccountDto.getBank() != null) {
            Bank bank = bankRepository.findById(bankAccountDto.getBank().getBankId()).orElseThrow(null);
            if (bank != null) {
                bankAccount.setBank(bank);
            }
        }

        // Set Account Details
        bankAccount.setAccountHolderName(bankAccountDto.getAccountHolderName());
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());

        // Set Created/Updated By
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (bankAccountDto.getBankAccountId() == null) {
                bankAccount.setCreatedBy(user.getUserId());
            }
            bankAccount.setUpdatedBy(user.getUserId());
        }

        bankAccountRepository.save(bankAccount);
    }
}
