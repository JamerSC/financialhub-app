package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.ClientAccountType;
import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {

    // Query to find all ClientAccount entries of type CASE, RETAINER, PROJECTS
    List<ClientAccount> findByClientAccountType(ClientAccountType clientAccountType);

    // Query to hide/disable those contacts/clients with existing retainer account
    @Query("SELECT ca.client.contactId FROM ClientAccount ca WHERE ca.clientAccountType = 'RETAINER'")
    List<Long> findClientIdsWithRetainers();
}
