package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //
}
