package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.Liquidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiquidationRepository extends JpaRepository<Liquidation, Long> {

    @Query("SELECT l FROM Liquidation l WHERE l.activity.pcActivityId = :id ORDER BY l.date DESC")
    List<Liquidation> findAllActivityEntriesByPettyCashActivityId(@Param("id") Long id);
}
