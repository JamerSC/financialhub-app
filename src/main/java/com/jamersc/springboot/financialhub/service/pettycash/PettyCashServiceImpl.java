package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.mapper.PettyCashMapper;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.FundRepository;
import com.jamersc.springboot.financialhub.repository.PettyCashRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
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
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PettyCashServiceImpl implements PettyCashService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PettyCashRepository pettyCashRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FundRepository fundRepository;

    @Override
    public List<PettyCashDto> getAllPettyCashRecord() {
        return pettyCashRepository.findAll().stream()
                .map(PettyCashMapper::toPettyCashDto)
                .collect(Collectors.toList());
    }

    @Override
    public PettyCashDto findPettyCashById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash ID not found!"));
        return PettyCashMapper.toPettyCashDto(pettyCash);
    }

    @Override
    public PettyCash findPettyCashLiquidationById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id).orElseThrow(() -> new RuntimeException("Petty Cash not found"));
        Hibernate.initialize(pettyCash.getLiquidations());
        //pettyCash.getLiquidations().size();
        return pettyCash;
    }

    @Override
    public void savePettyCashRecord(PettyCashDto pettyCashDto, String username) {
        PettyCash pettyCash;
        if (pettyCashDto.getPettyCashId() != null) {
            pettyCash = pettyCashRepository.findById(pettyCashDto.getPettyCashId()).orElse(new PettyCash());
            pettyCash.setVoucherNo(pettyCashDto.getVoucherNo());
            pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
            pettyCash.setDate(pettyCashDto.getDate());
            pettyCash.setActivityDescription(pettyCashDto.getActivityDescription());
            pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
            pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
            Fund manageFund = fundRepository.getReferenceById(pettyCashDto.getFund().getId());
            pettyCash.setFund(manageFund);
            User updatedBy = userRepo.findByUsername(username);
            if (updatedBy != null) {
                pettyCash.setUpdatedBy(updatedBy.getId());
            }
            System.out.println("Updated successfully! " + pettyCashDto);
        } else {
            pettyCash = new PettyCash();
            pettyCash.setVoucherNo(pettyCashDto.getVoucherNo());
            pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
            pettyCash.setDate(pettyCashDto.getDate());
            pettyCash.setActivityDescription(pettyCashDto.getActivityDescription());
            pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
            pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
            Fund manageFund = fundRepository.getReferenceById(pettyCashDto.getFund().getId());
            pettyCash.setFund(manageFund);
            User createdBy = userRepo.findByUsername(username);
            if (createdBy != null) {
                pettyCash.setCreatedBy(createdBy.getId());
                pettyCash.setUpdatedBy(createdBy.getId());
            } else {
                pettyCash.setCreatedBy(1L);
                pettyCash.setUpdatedBy(1L);
            }
            System.out.println("Created successfully! " + pettyCashDto);
        }
        //BeanUtils.copyProperties(pettyCash, pettyCashDto, "createdAt");
        pettyCashRepository.save(pettyCash);
    }

    @Override
    public void save(PettyCash pettyCash) {
        logger.info("Saving... " + pettyCash);
        pettyCashRepository.save(pettyCash);
    }

    @Override
    public void deletePettyCashRecordById(Long id) {
        System.out.println("Delete request for id: " + id);
        pettyCashRepository.deleteById(id);
    }
}
