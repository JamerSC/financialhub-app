package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;

import java.util.List;

public interface CheckService {

    List<Check> findAllCheckRecord();

    CheckDto findCheckRecordById(Integer id);

    void saveCheckRecord(CheckDto checkDto);

    void deleteCheckRecordById(Integer id);
}
