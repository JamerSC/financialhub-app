package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ContactSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactSubTypeRepository extends JpaRepository<ContactSubCategory, Long> {
    //
}
