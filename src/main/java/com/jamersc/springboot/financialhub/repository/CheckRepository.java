package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository <Check, Long> {
    // add custom query here..
}
