package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {
    //
}
