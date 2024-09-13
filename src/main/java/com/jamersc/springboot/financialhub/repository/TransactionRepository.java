package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //
    List<Transaction> findByBankAccount_Id(Long id);

    //List<Transaction> findByTransactionType(String transactionType);
    @Query("SELECT t FROM Transaction t WHERE t.transactionType = :transactionType")
    List<Transaction> findByTransactionType(@Param("transactionType") String transactionType);
}
