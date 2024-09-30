package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Cases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Cases, Long> {
    //
}
