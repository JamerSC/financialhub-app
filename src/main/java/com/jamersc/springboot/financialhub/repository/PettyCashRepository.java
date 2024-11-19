package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PettyCashRepository extends JpaRepository <PettyCash, Long> {

    // Method retrieves all PettyCash entries along with all related ClientAccount entities.
    //@Query("SELECT p FROM PettyCash p JOIN FETCH p.accounts a")
    //List<PettyCash> findAllWithClientAccounts();

    // Find by createdBy
    List<PettyCash> findByCreatedBy(Long createdBy);

    // Find by receivedBy
    List<PettyCash> findByReceivedBy(User receivedBy);

    // Find by both createdBy and receivedBy
    @Query("SELECT p FROM PettyCash p WHERE p.createdBy = :createdBy AND p.receivedBy = :receivedBy")
    List<PettyCash> findByCreatedByAndReceivedBy(@Param("createdBy") Long createdBy, @Param("receivedBy") User receivedBy);
}
