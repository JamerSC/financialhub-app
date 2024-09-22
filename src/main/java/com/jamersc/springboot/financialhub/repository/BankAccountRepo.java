package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
    //
}
