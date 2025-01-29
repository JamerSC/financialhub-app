package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
}
