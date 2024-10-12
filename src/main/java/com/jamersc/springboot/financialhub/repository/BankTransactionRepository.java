package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.bank.BankTransaction;
import com.jamersc.springboot.financialhub.model.bank.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByBankAccount_BankAccountId(Long bankAccountId);
    List<BankTransaction> findByTransactionType(String transactionType);
    List<BankTransaction> findByBankAccount_BankAccountIdAndTransactionType(Long bankAccountId, TransactionType transactionType);

}
