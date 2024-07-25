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
    public PettyCashDto findPettyCashRecordById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id).orElse(null);
        if (pettyCash != null) {
            PettyCashDto pettyCashDto = new PettyCashDto();
            BeanUtils.copyProperties(pettyCash, pettyCashDto);
            return pettyCashDto;
        }
        return null;
    }

    @Override
    public void savePettyCashRecord(PettyCashDto pettyCashDto, String username) {
        PettyCash pettyCash;
        if (pettyCashDto.getId() != null) {
            pettyCash = pettyCashRepository.findById(pettyCashDto.getId()).orElse(new PettyCash());
            pettyCash.setPcvNumber(pettyCashDto.getPcvNumber());
            pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
            pettyCash.setDate(pettyCashDto.getDate());
            pettyCash.setParticulars(pettyCashDto.getParticulars());
            pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
            pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                pettyCash.setUpdatedBy(Math.toIntExact(updatedBy.getId()));
            }
            System.out.println("Updated successfully! " + pettyCashDto);
        } else {
            pettyCash = new PettyCash();
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
            System.out.println("Created successfully! " + pettyCashDto);
        }
        //BeanUtils.copyProperties(pettyCash, pettyCashDto, "createdAt");
        pettyCashRepository.save(pettyCash);
    }

    @Override
    public void deletePettyCashRecordById(Long id) {
        System.out.println("Delete request for id: " + id);
        pettyCashRepository.deleteById(id);
    }
}
