package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactAdditionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactAdditionalDetailsRepo extends JpaRepository<ContactAdditionalDetails, Long> {
}
