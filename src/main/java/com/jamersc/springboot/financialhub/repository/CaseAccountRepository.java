package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.CaseAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseAccountRepository extends JpaRepository<CaseAccount, Long> {
    //
}
