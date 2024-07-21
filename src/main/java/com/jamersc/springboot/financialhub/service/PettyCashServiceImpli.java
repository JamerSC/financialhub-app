package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.PettyCashRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PettyCashServiceImpli implements PettyCashService {

    @Autowired
    private PettyCashRepository pettyCashRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PettyCash> findALlPettyCashRecord() {
        return pettyCashRepository.findAll();
    }

    @Override
    public PettyCashDto findPettyCashById(Integer id) {
        return null;
    }

    @Override
    public void savePettyCashRecord(PettyCashDto pettyCashDto, String username) {
        PettyCash pettyCash = new PettyCash();
        pettyCash.setPcvNumber(pettyCashDto.getPcvNumber());
        pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
        pettyCash.setDate(pettyCashDto.getDate());
        pettyCash.setParticulars(pettyCashDto.getParticulars());
        pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
        pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            pettyCash.setCreatedBy(Math.toIntExact(createdBy.getId()));
            pettyCash.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        } else {
            pettyCash.setCreatedBy(1);
            pettyCash.setUpdatedBy(1);
        }
        //BeanUtils.copyProperties(pettyCash, pettyCashDto, "createdAt");
        pettyCashRepository.save(pettyCash);
    }

    @Override
    public void deletePettyCashRecordById(Integer id) {

    }
}
