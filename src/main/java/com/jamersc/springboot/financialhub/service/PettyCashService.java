package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.entity.PettyCash;

import java.util.List;

public interface PettyCashService {

    List<PettyCash> findALlPettyCashRecord();

    PettyCashDto findPettyCashById(Integer id);

    void savePettyCashRecord(PettyCashDto pettyCashDto);

    void deletePettyCashRecordById(Integer id);
}
