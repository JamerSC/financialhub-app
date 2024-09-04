package com.jamersc.springboot.financialhub.service.cash;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.repository.LiquidationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class LiquidationServiceImpli implements LiquidationService{

    @Autowired
    private LiquidationRepository liquidationRepository;

    @Override
    public List<Liquidation> findByPettyCashVoucherId(Long id) {
        return liquidationRepository.findByPettyCash_Id(id);
    }

    @Override
    public Liquidation findLiquidationById(Long id) {
        return liquidationRepository.findById(id).orElseThrow(null);
    }

    @Override
    public void save(Liquidation liquidation) {
        if (liquidation.getPettyCash() != null && liquidation.getPettyCash().getId() != null) {
            // Ensure PettyCash is managed
            PettyCash managedPettyCash = liquidationRepository.getReferenceById(liquidation.getPettyCash().getId()).getPettyCash();
            liquidation.setPettyCash(managedPettyCash);
        }
        liquidationRepository.save(liquidation);
    }
}


     /*if (liquidation.getId() != null) {
            // Check if the entity exists in the database
            if (liquidationRepository.existsById(liquidation.getId())) {
                // Update existing entity
                Liquidation existingLiquidation = liquidationRepository.findById(liquidation.getId()).orElseThrow(() -> new RuntimeException("Entity not found"));
                existingLiquidation.setDate(liquidation.getDate());
                existingLiquidation.setItemDescription(liquidation.getItemDescription());
                existingLiquidation.setAmount(liquidation.getAmount());
                liquidationRepository.save(existingLiquidation);
            } else {
                // Handle case where entity does not exist
                liquidationRepository.save(liquidation);
            }
        } else {
            // Save new entity
            liquidationRepository.save(liquidation);
        }*/