package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long id);

    List<Transaction> findBankAccountById(Long id);

    List<Transaction> getTransactionType(String transactionType);

    void save(Transaction transaction);

    void deleteTransactionById(Long id);
}
