package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.bank.Transaction;
import com.jamersc.springboot.financialhub.model.bank.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBankAccount_Id(Long bankAccountId);
    List<Transaction> findByTransactionType(String transactionType);
    List<Transaction> findByBankAccount_IdAndTransactionType(Long bankAccountId, TransactionType transactionType);
}
