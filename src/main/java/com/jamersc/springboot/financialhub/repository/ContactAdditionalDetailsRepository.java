package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.contact.ContactAdditionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactAdditionalDetailsRepository extends JpaRepository<ContactAdditionalDetails, Long> {
}
