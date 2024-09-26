package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
    //
}
