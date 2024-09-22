package com.jamersc.springboot.financialhub.service.check;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CheckRepo;
import com.jamersc.springboot.financialhub.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CheckServiceImpl implements CheckService{

    private static final Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    private CheckRepo checkRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Check> getAllCheckRecord() {
        return checkRepo.findAll();
    }

    @Override
    public CheckDto findCheckRecordById(Long id) {
        Check check = checkRepo.findById(id).orElse(null);
        if (check != null) {
            CheckDto checkDto = new CheckDto();
            BeanUtils.copyProperties(check, checkDto);
            return checkDto;
        }
        return null;
    }

    @Override
    public void saveCheckRecord(CheckDto checkDto, String createdBy) {
        Check check = new Check();
        User creator = userRepo.findByUsername(createdBy);
        if (creator != null) {
            check.setCreatedBy(Math.toIntExact(creator.getId()));
            check.setUpdatedBy(Math.toIntExact(creator.getId()));
        } else {
            check.setCreatedBy(1);
            check.setUpdatedBy(1);
        }
        BeanUtils.copyProperties(checkDto, check);
        checkRepo.save(check);
    }

    @Override
    public void updateCheckRecord(CheckDto checkDto, String updatedBy) {
        Check check = checkRepo.findById(checkDto.getId()).orElse(null);
        if (check != null) {
            User updater = userRepo.findByUsername(updatedBy);
            if (updater != null) {
                check.setUpdatedBy(Math.toIntExact(updater.getId()));
            } else {
                check.setUpdatedBy(1);
            }
            BeanUtils.copyProperties(checkDto, check, "createdAt");
            checkRepo.save(check);
        } else {
            logger.error("Check with ID: " +  checkDto.getId() + " not found!");
        }
    }

    @Override
    public void deleteCheckRecordById(Long id) {
        checkRepo.deleteById(id);
    }
}
