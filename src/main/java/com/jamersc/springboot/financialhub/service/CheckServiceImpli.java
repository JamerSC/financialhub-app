package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.repository.CheckRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
//@AllArgsConstructor // Lombok
//@Slf4j // logger
public class CheckServiceImpli  {
    //implements CheckService
   /* private CheckRepository checkRepository;

    @Override
    public List<Check> getAllCheckRecord() {
        log.info("Get All Check Record");
        return checkRepository.findAll();
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
