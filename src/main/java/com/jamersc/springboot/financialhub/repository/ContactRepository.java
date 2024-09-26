package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    //
}
