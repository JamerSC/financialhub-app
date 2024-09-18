package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    //
}
