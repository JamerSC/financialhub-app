package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.model.bank.Transaction;
import com.jamersc.springboot.financialhub.model.bank.TransactionType;
import com.jamersc.springboot.financialhub.repository.BankAccountRepository;
import com.jamersc.springboot.financialhub.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaction ID not found."));
    }

    @Override
    public List<Transaction> findBankAccountById(Long bankAccountId) {
        return transactionRepository.findByBankAccount_Id(bankAccountId);
    }

    @Override
    public List<Transaction> getTransactionType(String transactionType) {
        return transactionRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<Transaction> findTransactionsByBankAccountAndType(Long bankAccountId, TransactionType transactionType) {
        return transactionRepository.findByBankAccount_IdAndTransactionType(bankAccountId, transactionType);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deposit(Transaction deposit) {
        BankAccount account = bankAccountRepository.findById(deposit.getBankAccount().getId())
                .orElseThrow(() -> new RuntimeException("Bank Account ID not found."));
        // Total account balance = account balance + deposit amount
        account.setAccountBalance(account.getAccountBalance() + deposit.getTransactionAmount());
        bankAccountRepository.save(account); // save deposit to bank account based on id

        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(deposit.getTransactionDate());
        transaction.setTransactionAmount(deposit.getTransactionAmount());
        transaction.setTransactionNote(deposit.getTransactionNote());
        transactionRepository.save(transaction); // save deposit transaction
    }

    @Override
    public void withdraw(Transaction withdraw) {
        BankAccount account = bankAccountRepository.findById(withdraw.getBankAccount().getId())
                .orElseThrow(() -> new RuntimeException("Bank Account ID not found."));
        if (account.getAccountBalance() < withdraw.getTransactionAmount()) {
            throw new RuntimeException("Insufficient funds.!");
        }
        // total account balance = account balance - withdraw amount
        account.setAccountBalance(account.getAccountBalance() - withdraw.getTransactionAmount());
        bankAccountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setTransactionDate(withdraw.getTransactionDate());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionAmount(withdraw.getTransactionAmount());
        transaction.setTransactionNote(withdraw.getTransactionNote());
        transactionRepository.save(transaction); // save deposit transaction
    }


    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }


}
