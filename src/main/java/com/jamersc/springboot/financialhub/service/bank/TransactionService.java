package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.Transaction;
import com.jamersc.springboot.financialhub.model.TransactionType;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long transactionId);

    List<Transaction> findBankAccountById(Long bankAccountId);

    List<Transaction> getTransactionType(String transactionType);

    List<Transaction> findTransactionsByBankAccountAndType(Long bankAccountId, TransactionType transactionType);  // New method

    void save(Transaction transaction);

    void deleteTransactionById(Long transactionId);
}
