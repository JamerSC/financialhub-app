package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository <CreditCard, Long> {
    // add custom query here..
}
