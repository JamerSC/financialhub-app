package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    //
    @Query("SELECT c FROM Contact c LEFT JOIN FETCH c.clientAccounts WHERE c.contactId = :id")
    Contact findByIdWithAccounts(@Param("id") Long id);
}
