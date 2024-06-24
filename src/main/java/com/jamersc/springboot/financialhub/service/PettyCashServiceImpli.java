package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PettyCashServiceImpli {

    // implements PettyCashService
/*    private PettyCashService pettyCashService;

    @Autowired
    public PettyCashServiceImpli(PettyCashService pettyCashService) {
        this.pettyCashService = pettyCashService;
    }

    @Override
    public List<PettyCash> findALlPettyCashRecord() {
        return null;
    }

    @Override
    public PettyCashDto findPettyCashById(Integer id) {
        return null;
    }

    @Override
    public void savePettyCashRecord(PettyCashDto pettyCashDto) {

    }

    @Override
    public void deletePettyCashRecordById(Integer id) {

    }*/
}
