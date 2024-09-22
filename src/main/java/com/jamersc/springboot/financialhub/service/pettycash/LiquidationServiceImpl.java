package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.repository.LiquidationRepo;
import com.jamersc.springboot.financialhub.repository.PettyCashRepo;
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
    private LiquidationRepo liquidationRepo;

    @Autowired
    private PettyCashRepo pettyCashRepo;

    @Override
    public List<Liquidation> findByPettyCashVoucherId(Long id) {
        return liquidationRepo.findByPettyCash_Id(id);
    }

    @Override
    public Liquidation findLiquidationById(Long id) {
        return liquidationRepo.findById(id).orElseThrow(null);
    }

    @Override
    public void save(Liquidation liquidation) {
        if (liquidation.getPettyCash() != null && liquidation.getPettyCash().getId() != null) {
            // Ensure PettyCash is managed
            PettyCash managedPettyCash = pettyCashRepo.getReferenceById(liquidation.getPettyCash().getId());
            liquidation.setPettyCash(managedPettyCash);
        }
        liquidationRepo.save(liquidation);
    }

    @Override
    public void deleteLiquidationItemById(Long id) {
        liquidationRepo.deleteById(id);
    }
}
