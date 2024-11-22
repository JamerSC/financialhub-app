package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
@Service
public class LiquidationServiceImpl {
    //implements LiquidationService
/*
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
        if (liquidation.getPettyCash() != null && liquidation.getPettyCash().getPettyCashId() != null) {
            // Ensure PettyCash is managed
            PettyCash managedPettyCash = pettyCashRepository.getReferenceById(liquidation.getPettyCash().getPettyCashId());
            liquidation.setPettyCash(managedPettyCash);
        }
        liquidationRepository.save(liquidation);
    }

    @Override
    public void deleteLiquidationItemById(Long id) {
        liquidationRepository.deleteById(id);
    }*/
}
