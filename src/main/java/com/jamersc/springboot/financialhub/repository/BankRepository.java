package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    //
}
