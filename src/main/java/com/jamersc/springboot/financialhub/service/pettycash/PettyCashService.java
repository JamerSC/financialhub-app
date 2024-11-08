package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;

import java.util.List;

public interface PettyCashService {

    List<PettyCashDto> getAllPettyCashRecord();

    PettyCashDto findPettyCashById(Long id);

    PettyCash findPettyCashLiquidationById(Long id);

    void savePettyCashRecord(PettyCashDto pettyCashDto, String username);

    void save(PettyCash pettyCash);

    void deletePettyCashRecordById(Long id);
}
