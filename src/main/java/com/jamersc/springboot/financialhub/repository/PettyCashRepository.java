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

    @Query("SELECT p FROM PettyCash p ORDER BY p.date DESC")
    List<PettyCash> fillAllPettyCashDateDesc();

    // Find by both createdBy and receivedBy
    //@Query("SELECT p FROM PettyCash p WHERE p.createdBy = :createdBy AND p.receivedBy = :receivedBy")
    //List<PettyCash> findByCreatedByAndReceivedBy(@Param("createdBy") Long createdBy, @Param("receivedBy") User receivedBy);

    // Find all unapproved petty cash entries for a specific receivedBy user
    //@Query("SELECT p FROM PettyCash p WHERE p.approved = false AND p.receivedBy = :receivedBy")
    //List<PettyCash> findUnapprovedPettyCashByReceivedBy(@Param("receivedBy") User receivedBy);

    // Find all unapproved petty cash entries for a specific receivedBy user desc order by Date
    @Query("SELECT p FROM PettyCash p WHERE p.approved = false " +
            "AND p.receivedBy = :receivedBy ORDER BY p.date DESC")
    List<PettyCash> findUnapprovedPettyCashByReceivedByDateDesc(@Param("receivedBy") User receivedBy);

    // Find all approved petty cash entries for a specific receivedBy user
    //@Query("SELECT p FROM PettyCash p WHERE p.approved = true AND p.receivedBy = :receivedBy")
    //List<PettyCash> findApprovedPettyCashByReceivedBy(@Param("receivedBy") User receivedBy);

    // Find all approved petty cash entries for a specific receivedBy user desc order by Date
    @Query("SELECT p FROM PettyCash p WHERE p.approved = true " +
            "AND p.receivedBy = :receivedBy ORDER BY p.date DESC")
    List<PettyCash> findApprovedPettyCashByReceivedByDateDesc(@Param("receivedBy") User receivedBy);

    // Find All Petty Cash Approved and Client Account ID in descending order by Date request.
    @Query("SELECT p FROM PettyCash p JOIN p.accounts a " +
            "WHERE p.approved = true AND a.clientAccountId = :clientAccountId ORDER BY p.date DESC")
    List<PettyCash> findByClientAccountId(@Param("clientAccountId") Long clientAccountId);
}
