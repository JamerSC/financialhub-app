package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    //
}
