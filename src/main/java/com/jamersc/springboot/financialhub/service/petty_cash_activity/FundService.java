package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.model.Fund;

import java.util.List;

public interface FundService {

    List<Fund> getAllFund();
    FundDto getFundById(Long id);
    void save(Fund fund);
}
