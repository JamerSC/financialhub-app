package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactCategory, Long> {
    //
}
