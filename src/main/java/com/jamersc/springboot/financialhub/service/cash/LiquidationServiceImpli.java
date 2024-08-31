package com.jamersc.springboot.financialhub.service.cash;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.repository.LiquidationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LiquidationServiceImpli implements LiquidationService{

    @Autowired
    private LiquidationRepository liquidationRepository;

    @Override
    public List<Liquidation> findByPettyCashVoucherId(Long id) {
        return liquidationRepository.findByPettyCash_Id(id);
    }

    @Override
    public void save(Liquidation liquidation) {
        liquidationRepository.save(liquidation);
    }

    @Override
    public void saveAll(List<Liquidation> liquidations) {
        liquidationRepository.saveAll(liquidations);
    }
}
