package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.PettyCash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PettyCashRepo extends JpaRepository <PettyCash, Long> {
    // add custom query here..
}
