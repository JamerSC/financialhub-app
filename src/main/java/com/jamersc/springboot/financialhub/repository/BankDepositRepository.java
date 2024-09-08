package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.BankDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankDepositRepository extends JpaRepository<BankDeposit, Long> {
    //
    List<BankDeposit> findByBankAccount_Id(Long id);
}
