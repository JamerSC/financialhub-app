package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //
    List<Transaction> findByBankAccount_Id(Long id);
}
