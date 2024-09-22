package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.FundRepo;
import com.jamersc.springboot.financialhub.repository.PettyCashRepo;
import com.jamersc.springboot.financialhub.repository.UserRepo;
import com.jamersc.springboot.financialhub.service.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PettyCashServiceImpl implements PettyCashService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PettyCashRepo pettyCashRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FundRepo fundRepo;

    @Override
    public List<PettyCash> getAllPettyCashRecord() {
        return pettyCashRepo.findAll();
    }

    @Override
    public PettyCashDto findPettyCashRecordById(Long id) {
        PettyCash pettyCash = pettyCashRepo.findById(id).orElse(null);
        if (pettyCash != null) {
            PettyCashDto pettyCashDto = new PettyCashDto();
            BeanUtils.copyProperties(pettyCash, pettyCashDto);
            return pettyCashDto;
        }
        return null;
    }

    @Override
    public PettyCash findPettyCashById(Long id) {
        PettyCash pettyCash = pettyCashRepo.findById(id).orElseThrow(() -> new RuntimeException("Petty Cash not found"));
        Hibernate.initialize(pettyCash.getLiquidations());
        //pettyCash.getLiquidations().size();
        return pettyCash;
    }

    @Override
    public void savePettyCashRecord(PettyCashDto pettyCashDto, String username) {
        PettyCash pettyCash;
        if (pettyCashDto.getId() != null) {
            pettyCash = pettyCashRepo.findById(pettyCashDto.getId()).orElse(new PettyCash());
            pettyCash.setPcvNumber(pettyCashDto.getPcvNumber());
            pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
            pettyCash.setDate(pettyCashDto.getDate());
            pettyCash.setParticulars(pettyCashDto.getParticulars());
            pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
            pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
            Fund manageFund = fundRepo.getReferenceById(pettyCashDto.getFund().getId());
            pettyCash.setFund(manageFund);
            User updatedBy = userRepo.findByUsername(username);
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
            Fund manageFund = fundRepo.getReferenceById(pettyCashDto.getFund().getId());
            pettyCash.setFund(manageFund);
            User createdBy = userRepo.findByUsername(username);
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
        pettyCashRepo.save(pettyCash);
    }

    @Override
    @Transactional
    public void save(PettyCash pettyCash) {
        logger.info("Saving... " + pettyCash);
        pettyCashRepo.save(pettyCash);
    }

    @Override
    public void deletePettyCashRecordById(Long id) {
        System.out.println("Delete request for id: " + id);
        pettyCashRepo.deleteById(id);
    }
}
