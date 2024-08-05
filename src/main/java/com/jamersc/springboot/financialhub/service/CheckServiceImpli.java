package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CheckRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CheckServiceImpli implements CheckService{

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Check> getAllCheckRecord() {
        return checkRepository.findAll();
    }

    @Override
    public CheckDto findCheckRecordById(Long id) {
        Check check = checkRepository.findById(id).orElse(null);
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
        User creator = userRepository.findByUsername(createdBy);
        if (creator != null) {
            check.setCreatedBy(Math.toIntExact(creator.getId()));
            check.setUpdatedBy(Math.toIntExact(creator.getId()));
        } else {
            check.setCreatedBy(1);
            check.setUpdatedBy(1);
        }
        BeanUtils.copyProperties(checkDto, check);
        checkRepository.save(check);
    }

    @Override
    public void updateCheckRecord(CheckDto checkDto, String updatedBy) {

    }


    @Override
    public void deleteCheckRecordById(Long id) {
        checkRepository.deleteById(id);
    }
}
