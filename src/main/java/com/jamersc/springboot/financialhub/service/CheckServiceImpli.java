package com.jamersc.springboot.financialhub.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
