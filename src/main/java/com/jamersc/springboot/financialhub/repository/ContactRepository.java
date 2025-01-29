package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Find all internal contacts
    @Query("SELECT c FROM Contact c WHERE c.contactCategoryType = 'INTERNAL'")
    List<Contact> findContactsWithInternalCategory();

    // Find contacts with client accounts by ID
    @Query("SELECT c FROM Contact c LEFT JOIN FETCH c.clientAccounts WHERE c.contactId = :id")
    Contact findByIdWithAccounts(@Param("id") Long id);

    // Find all internal contacts w/o user access
    @Query("SELECT c FROM Contact c WHERE c.contactCategoryType = 'INTERNAL' AND c.user IS NULL")
    List<Contact> findContactsWithInternalCategoryAndNullUser();

    // Find all internal contacts with user access
    @Query("SELECT c FROM Contact c WHERE c.contactCategoryType = 'INTERNAL' AND c.user IS NOT NULL")
    List<Contact> findContactsWithInternalCategoryAndNotNullUser();
}
