package com.jamersc.springboot.financialhub.service.check;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;

import java.util.List;

public interface CheckService {

    List<Check> getAllCheckRecord();

    CheckDto findCheckRecordById(Long id);

    void saveCheckRecord(CheckDto checkDto, String createdBy);

    void updateCheckRecord(CheckDto checkDto, String updatedBy);

    void deleteCheckRecordById(Long id);
}
