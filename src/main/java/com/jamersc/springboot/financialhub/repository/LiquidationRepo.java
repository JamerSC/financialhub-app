package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Liquidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiquidationRepo extends JpaRepository<Liquidation, Long> {
    List<Liquidation> findByPettyCash_Id(Long id);
}
