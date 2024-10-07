package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.CaseAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<CaseAccount, Long> {
    //
}
