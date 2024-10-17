package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.BankTransaction;
import com.jamersc.springboot.financialhub.model.BankTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByBankAccount_BankAccountId(Long bankAccountId);
    List<BankTransaction> findByTransactionType(String transactionType);
    List<BankTransaction> findByBankAccount_BankAccountIdAndTransactionType(Long bankAccountId, BankTransactionType transactionType);

}
