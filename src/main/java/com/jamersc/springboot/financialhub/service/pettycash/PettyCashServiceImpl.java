package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.FundMapper;
import com.jamersc.springboot.financialhub.mapper.PettyCashMapper;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import com.jamersc.springboot.financialhub.repository.FundRepository;
import com.jamersc.springboot.financialhub.repository.PettyCashRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PettyCashServiceImpl implements PettyCashService {

    private static final Logger logger = LoggerFactory.getLogger(PettyCashServiceImpl.class);
    @Autowired
    private PettyCashRepository pettyCashRepository;
    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FundRepository fundRepository;

    @Override
    public List<PettyCashDto> getAllPettyCash() {
        logger.info("Get all petty cash records.");
        return pettyCashRepository.findAll().stream()
                .map(PettyCashMapper::toPettyCashDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PettyCash> getAllPettyCashWithClientAccounts() {
        return pettyCashRepository.findAllWithClientAccounts();
    }

    @Override
    public PettyCashDto getPettyCashById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash ID not found!"));
        // Convert to Dto Accounts, Petty Cash, & Fund
        Set<ClientAccountDto> accounts = pettyCash.getAccounts()
                .stream().
                map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toSet());

        PettyCashDto dto = PettyCashMapper.toPettyCashDto(pettyCash);
        dto.setAccounts(accounts);

        FundDto fund = FundMapper.toFundDto(pettyCash.getFund());
        dto.setFund(fund);

        logger.info("Petty Cash: " + dto);
        return dto;
    }

    @Override
    public PettyCash getPettyCashLiquidationById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash not found"));
        Hibernate.initialize(pettyCash.getLiquidations());
        //pettyCash.getLiquidations().size();
        return pettyCash;
    }

    @Override
    public void savePettyCash(PettyCashDto dto, String username) {
        PettyCash pettyCash;

        if (dto.getPettyCashId() != null) {
            // UPDATE PETTY CASH
            pettyCash = pettyCashRepository.findById(dto.getPettyCashId())
                    .orElse(new PettyCash());

            //Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            if (dto.getFund() != null) {
                Fund fund = FundMapper.toFundEntity(dto.getFund());
                Fund fundId = fundRepository.findById(fund.getFundId()).orElse(null);
                pettyCash.setFund(fundId);
            }

            pettyCash.setVoucherNo(dto.getVoucherNo());
            pettyCash.setReceivedBy(dto.getReceivedBy());
            pettyCash.setDate(dto.getDate());
            pettyCash.setActivityDescription(dto.getActivityDescription());
            pettyCash.setActivityCategory(dto.getActivityCategory());
            pettyCash.setSoaCategory(dto.getSoaCategory());

            /*Set<ClientAccount> accounts = dto.getAccounts().stream()
                    .map(ClientAccountDto::getClientAccountId)
                    .map(clientAccountRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            pettyCash.setAccounts(accounts);*/

            pettyCash.setTotalAmount(dto.getTotalAmount());
            pettyCash.setReceivedBy(dto.getReceivedBy());
            pettyCash.setApproved(dto.getApproved());
            pettyCash.setApprovedBy(pettyCash.getApprovedBy());

            User updatedBy = userRepo.findByUsername(username);
            if (updatedBy != null) {
                pettyCash.setUpdatedBy(updatedBy.getUserId());
            }

            logger.info("Successfully updated petty cash: " + pettyCash);

        } else {
            // CREATE NEW PETTY CASH

            pettyCash = new PettyCash();

            Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            pettyCash.setFund(manageFund);

            pettyCash.setVoucherNo(dto.getVoucherNo());
            pettyCash.setReceivedBy(dto.getReceivedBy());
            pettyCash.setDate(dto.getDate());
            pettyCash.setActivityDescription(dto.getActivityDescription());
            pettyCash.setActivityCategory(dto.getActivityCategory());
            pettyCash.setSoaCategory(dto.getSoaCategory());

            Set<ClientAccount> accounts = dto.getAccounts().stream()
                    .map(ClientAccountDto::getClientAccountId)
                    .map(clientAccountRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            pettyCash.setAccounts(accounts);

            pettyCash.setTotalAmount(dto.getTotalAmount());
            pettyCash.setReceivedBy(dto.getReceivedBy()); // receiver
            pettyCash.setApproved(dto.getApproved());

            User createdBy = userRepo.findByUsername(username);
            if (createdBy != null) {
                pettyCash.setApprovedBy(createdBy.getUserId()); // approved by
                pettyCash.setCreatedBy(createdBy.getUserId()); // created
                pettyCash.setUpdatedBy(createdBy.getUserId()); // updated
            }
            logger.info("Successfully created new petty cash: " + pettyCash);
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
