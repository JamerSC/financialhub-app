package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactCompanyRepository extends JpaRepository<ContactCompany, Long> {
}
