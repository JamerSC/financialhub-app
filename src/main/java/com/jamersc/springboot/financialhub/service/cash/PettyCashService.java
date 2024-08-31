package com.jamersc.springboot.financialhub.service.cash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;

import java.util.List;
import java.util.Optional;

public interface PettyCashService {

    List<PettyCash> getAllPettyCashRecord();

    PettyCashDto findPettyCashRecordById(Long id);

    PettyCash findPettyCashById(Long id);

    void savePettyCashRecord(PettyCashDto pettyCashDto, String username);

    void save(PettyCash pettyCash);

    void deletePettyCashRecordById(Long id);
}
