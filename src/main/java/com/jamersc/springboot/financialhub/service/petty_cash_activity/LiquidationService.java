package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.model.Liquidation;

import java.util.List;

public interface LiquidationService {

    List<Liquidation> findByPettyCashVoucherId(Long id);

    Liquidation findLiquidationById(Long id);

    void save(Liquidation liquidation);

    void deleteLiquidationItemById(Long id);

}
