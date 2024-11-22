package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.FundMapper;
import com.jamersc.springboot.financialhub.mapper.PettyCashMapper;
import com.jamersc.springboot.financialhub.mapper.UserMapper;
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
    private UserRepository userRepository;
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
    public List<PettyCash> getUnapprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role -> role.getName()
                        .equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            return pettyCashRepository.fillAllPettyCashDateDesc();
        } else  {
            //
            return pettyCashRepository.findUnapprovedPettyCashByReceivedByDateDesc(user);
        }
    }

    @Override
    public List<PettyCash> getApprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role -> role.getName()
                        .equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            return pettyCashRepository.fillAllPettyCashDateDesc();
        } else  {
            //
            return pettyCashRepository.findApprovedPettyCashByReceivedByDateDesc(user);
        }
    }

    @Override
    public List<PettyCash> getByClientAccountId(Long id) {
        return  pettyCashRepository.findByClientAccountId(id);
    }

    @Override
    public List<PettyCash> getAllPettyCashWithClientAccounts() {
        return pettyCashRepository.findAll();
    }

    @Override
    public PettyCashDto getPettyCashById(Long id) {
        PettyCash pettyCash = pettyCashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash ID not found!"));
        // Convert to Dto Accounts, Petty Cash, & Fund

        PettyCashDto dto = PettyCashMapper.toPettyCashDto(pettyCash);

        Set<ClientAccountDto> accounts = pettyCash.getAccounts()
                .stream().
                map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toSet());
        dto.setAccounts(accounts);

        UserDto user = UserMapper.toUserDto(pettyCash.getReceivedBy());
        dto.setReceivedBy(user);

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

            /* USER Received By */
            if (dto.getReceivedBy() != null) {
                pettyCash.setReceivedBy(UserMapper.toUserEntity(dto.getReceivedBy()));
            }

            User updatedBy = userRepository.findByUsername(username);

            if (updatedBy != null) {
                pettyCash.setUpdatedBy(updatedBy.getUserId());
            }

            if (dto.getApproved() && updatedBy != null) {
                pettyCash.setApprovedBy(updatedBy.getUserId());
                pettyCash.setApproved(dto.getApproved());
            } else  {
                pettyCash.setApprovedBy(null);
                pettyCash.setApproved(false);
            }

            logger.info("Successfully updated petty cash: " + pettyCash);

        } else {
            // CREATE NEW PETTY CASH

            pettyCash = new PettyCash();

            Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            pettyCash.setFund(manageFund);

            pettyCash.setVoucherNo(dto.getVoucherNo());
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
            pettyCash.setApproved(dto.getApproved());

            /* USER Received By */
            User user = UserMapper.toUserEntity(dto.getReceivedBy());
            pettyCash.setReceivedBy(user);

            User createdBy = userRepository.findByUsername(username);
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
    public void saveAdminPettyCash(PettyCashDto dto, String username) {
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
            //pettyCash.setApproved(dto.getApproved());
            //pettyCash.setApprovedBy(dto.getApprovedBy());

            if (dto.getReceivedBy() != null) {
                pettyCash.setReceivedBy(UserMapper.toUserEntity(dto.getReceivedBy()));
            }

            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                pettyCash.setUpdatedBy(updatedBy.getUserId());
            }

            logger.info("Successfully updated petty cash cash admin works: " + pettyCash);

        } else {
            // CREATE NEW PETTY CASH

            pettyCash = new PettyCash();

            Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            pettyCash.setFund(manageFund);

            pettyCash.setVoucherNo(dto.getVoucherNo());
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
            pettyCash.setApproved(false);
            pettyCash.setApprovedBy(null); // approved by

            User createdBy = userRepository.findByUsername(username);

            if (createdBy != null) {
                pettyCash.setReceivedBy(createdBy); // receiver
                pettyCash.setCreatedBy(createdBy.getUserId()); // created
                pettyCash.setUpdatedBy(createdBy.getUserId()); // updated
            }
            logger.info("Successfully created new petty cash admin works: " + pettyCash);
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
