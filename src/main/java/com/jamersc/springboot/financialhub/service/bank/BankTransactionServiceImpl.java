package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.config.InsufficientFundsException;
import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.mapper.BankTransactionMapper;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import com.jamersc.springboot.financialhub.model.BankTransactionType;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.repository.BankTransactionRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(BankTransactionServiceImpl.class);
    @Autowired
    private BankTransactionRepository bankTransactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankTransactionMapper bankTransactionMapper;

    @Override
    public List<BankTransaction> getAllTransactions() {
        return bankTransactionRepository.findAll();
    }

    @Override
    public BankTransactionDto getTransactionById(Long transactionId) {
        BankTransaction bankTransaction = bankTransactionRepository.findById(transactionId).orElse(null);
        if (bankTransaction != null ) {
            //logger.info("Bank transaction Details " + bankTransactionDto);
            return bankTransactionMapper.toTransactionDto(bankTransaction);
        }
        throw new RuntimeException("Bank transaction details not found.");
        //return bankTransactionRepository.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaction ID not found."));
    }

    @Override
    public List<BankTransaction> findBankAccountById(Long bankAccountId) {
        return bankTransactionRepository.findByBankAccount_BankAccountId(bankAccountId);
    }

    @Override
    public List<BankTransaction> getTransactionType(String transactionType) {
        return bankTransactionRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<BankTransaction> findTransactionsByBankAccountAndType(Long bankAccountId, BankTransactionType transactionType) {
        return bankTransactionRepository.findByBankAccount_BankAccountIdAndTransactionType(bankAccountId, transactionType);
    }

    @Override
    public void save(BankTransactionDto bankTransactionDto, String username) {
        BankTransaction bankTransaction = new BankTransaction();
        BeanUtils.copyProperties(bankTransactionDto, bankTransaction);
        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public void processDeposit(BankTransactionDto deposit, String username) {
        User user = userRepository.findByUsername(username);
        BankTransaction depositTransaction;

        BankAccount account = bankAccountRepository.findById(deposit.getBankAccount()
                .getBankAccountId()).orElseThrow(() -> new RuntimeException("Bank account ID not found."));
        Double currentBalance = account.getAccountBalance();

        if (currentBalance == null) {
            currentBalance = 0.00;
        }

        account.setAccountBalance(currentBalance + deposit.getTransactionAmount());
        if (user != null) {
            account.setUpdatedBy(user.getUserId());
        }
        logger.info("PROCESS DEPOSIT: Bank Account Info: " + account);
        bankAccountRepository.save(account);

        depositTransaction = new BankTransaction();
        depositTransaction.setBankAccount(account);
        depositTransaction.setTransactionType(BankTransactionType.DEPOSIT);
        depositTransaction.setTransactionDate(deposit.getTransactionDate());
        depositTransaction.setTransactionAmount(deposit.getTransactionAmount());
        depositTransaction.setTransactionNote(deposit.getTransactionNote());
        if (user != null) {
            depositTransaction.setCreatedBy(user.getUserId());
            depositTransaction.setUpdatedBy(user.getUserId());
        }
        logger.info("PROCESS DEPOSIT: Bank Account Transaction: "+ depositTransaction);
        bankTransactionRepository.save(depositTransaction); // save deposit transaction
    }

    @Override
    public void processWithdrawal(BankTransactionDto withdraw, String username) {
        BankTransaction withdrawTransaction;
        User user = userRepository.findByUsername(username);

        BankAccount account = bankAccountRepository.findById(withdraw.getBankAccount()
                        .getBankAccountId()).orElseThrow(() -> new RuntimeException("Bank Account ID not found."));

        Double currentBalance = account.getAccountBalance();
        if (currentBalance < withdraw.getTransactionAmount()) {
            throw new InsufficientFundsException("Insufficient funds to complete the transactions.");
        }
        // total account balance = account balance - withdraw amount
        account.setAccountBalance(account.getAccountBalance() - withdraw.getTransactionAmount());
        if (user != null) {
            account.setUpdatedBy(user.getUserId());
        }
        logger.info("PROCESS WITHDRAWAL: Bank Account Info: " + account);
        bankAccountRepository.save(account);

        withdrawTransaction = new BankTransaction();
        withdrawTransaction.setBankAccount(account);
        withdrawTransaction.setTransactionDate(withdraw.getTransactionDate());
        withdrawTransaction.setTransactionType(BankTransactionType.WITHDRAWAL);
        withdrawTransaction.setTransactionAmount(withdraw.getTransactionAmount());
        withdrawTransaction.setTransactionNote(withdraw.getTransactionNote());
        if (user != null) {
            withdrawTransaction.setCreatedBy(user.getUserId());
            withdrawTransaction.setUpdatedBy(user.getUserId());
        }
        logger.info("PROCESS WITHDRAWAL: Bank Account Transaction: " + withdrawTransaction);
        bankTransactionRepository.save(withdrawTransaction); // save deposit transaction
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        bankTransactionRepository.deleteById(transactionId);
    }


}
