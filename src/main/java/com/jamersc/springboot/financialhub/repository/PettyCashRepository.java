package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.PettyCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PettyCashRepository extends JpaRepository <PettyCash, Long> {

    // Method retrieves all PettyCash entries along with all related ClientAccount entities.
    @Query("SELECT p FROM PettyCash p JOIN FETCH p.accounts a")
    List<PettyCash> findAllWithClientAccounts();


}
