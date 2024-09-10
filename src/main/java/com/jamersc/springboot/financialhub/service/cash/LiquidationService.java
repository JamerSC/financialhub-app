package com.jamersc.springboot.financialhub.service.cash;

import com.jamersc.springboot.financialhub.model.Liquidation;

import java.util.List;
import java.util.Optional;

public interface LiquidationService {

    List<Liquidation> findByPettyCashVoucherId(Long id);

    Liquidation findLiquidationById(Long id);

    void save(Liquidation liquidation);

    void deleteLiquidationItemById(Long id);

}
