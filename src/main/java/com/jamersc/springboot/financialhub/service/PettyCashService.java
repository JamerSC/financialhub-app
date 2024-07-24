package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;

import java.util.List;

public interface PettyCashService {

    List<PettyCash> findALlPettyCashRecord();

    PettyCashDto findPettyCashById(Long id);

    void savePettyCashRecord(PettyCashDto pettyCashDto, String username);

    void deletePettyCashRecordById(Long id);
}
