package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.BankAccount;
import com.jamersc.springboot.financialhub.model.Transaction;
import com.jamersc.springboot.financialhub.model.TransactionType;
import com.jamersc.springboot.financialhub.repository.BankAccountRepo;
import com.jamersc.springboot.financialhub.repository.TransactionRepo;
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
    private TransactionRepo transactionRepo;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepo.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaction ID not found."));
    }

    @Override
    public List<Transaction> findBankAccountById(Long bankAccountId) {
        return transactionRepo.findByBankAccount_Id(bankAccountId);
    }

    @Override
    public List<Transaction> getTransactionType(String transactionType) {
        return transactionRepo.findByTransactionType(transactionType);
    }

    @Override
    public List<Transaction> findTransactionsByBankAccountAndType(Long bankAccountId, TransactionType transactionType) {
        return transactionRepo.findByBankAccount_IdAndTransactionType(bankAccountId, transactionType);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    @Override
    public void deposit(Transaction deposit) {
        BankAccount account = bankAccountRepo.findById(deposit.getBankAccount().getId())
                .orElseThrow(() -> new RuntimeException("Bank Account ID not found."));
        // Total account balance = account balance + deposit amount
        account.setAccountBalance(account.getAccountBalance() + deposit.getTransactionAmount());
        bankAccountRepo.save(account); // save deposit to bank account based on id

        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(deposit.getTransactionDate());
        transaction.setTransactionAmount(deposit.getTransactionAmount());
        transaction.setTransactionNote(deposit.getTransactionNote());
        transactionRepo.save(transaction); // save deposit transaction
    }

    @Override
    public void withdraw(Transaction withdraw) {
        BankAccount account = bankAccountRepo.findById(withdraw.getBankAccount().getId())
                .orElseThrow(() -> new RuntimeException("Bank Account ID not found."));
        if (account.getAccountBalance() < withdraw.getTransactionAmount()) {
            throw new RuntimeException("Insufficient funds.!");
        }
        // total account balance = account balance - withdraw amount
        account.setAccountBalance(account.getAccountBalance() - withdraw.getTransactionAmount());
        bankAccountRepo.save(account);

        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setTransactionDate(withdraw.getTransactionDate());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionAmount(withdraw.getTransactionAmount());
        transaction.setTransactionNote(withdraw.getTransactionNote());
        transactionRepo.save(transaction); // save deposit transaction
    }


    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepo.deleteById(transactionId);
    }


}
