package com.jamersc.springboot.financialhub.service.cash;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.repository.LiquidationRepository;
import com.jamersc.springboot.financialhub.repository.PettyCashRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class LiquidationServiceImpl implements LiquidationService{

    @Autowired
    private LiquidationRepository liquidationRepository;

    @Autowired
    private PettyCashRepository pettyCashRepository;

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
            PettyCash managedPettyCash = pettyCashRepository.getReferenceById(liquidation.getPettyCash().getId());
            liquidation.setPettyCash(managedPettyCash);
        }
        liquidationRepository.save(liquidation);
    }
}
