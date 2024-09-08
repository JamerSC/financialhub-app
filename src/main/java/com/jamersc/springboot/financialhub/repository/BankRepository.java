package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {
    //
    @Query("SELECT b FROM Bank b JOIN FETCH b.accounts")
    List<Bank> findAllWithAccounts();
 }
