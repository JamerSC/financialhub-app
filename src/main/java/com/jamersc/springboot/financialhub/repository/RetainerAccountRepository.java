package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetainerAccountRepository extends JpaRepository<RetainerAccount, Long> {
    //
}
