package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.repository.CheckRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CheckServiceImpli {
    //implements CheckService
/*
    private CheckRepository checkRepository;

    @Autowired
    public CheckServiceImpli(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Override
    public List<Check> findAllCheckRecord() {
        return null;
    }

    @Override
    public CheckDto findCheckRecordById(Integer id) {
        return null;
    }

    @Override
    public void saveCheckRecord(CheckDto checkDto) {

    }

    @Override
    public void deleteCheckRecordById(Integer id) {

    }*/
}
