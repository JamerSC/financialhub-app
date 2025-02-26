package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.FundMapper;
import com.jamersc.springboot.financialhub.mapper.PettyCashMapper;
import com.jamersc.springboot.financialhub.mapper.UserMapper;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import com.jamersc.springboot.financialhub.repository.FundRepository;
import com.jamersc.springboot.financialhub.repository.PettyCashActivityRepository;
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
public class PettyCashActivityServiceImpl implements PettyCashActivityService {

    private static final Logger logger = LoggerFactory.getLogger(PettyCashActivityServiceImpl.class);
    @Autowired
    private PettyCashActivityRepository pettyCashActivityRepository;
    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private PettyCashMapper pettyCashMapper;
    @Autowired
    private ClientAccountMapper clientAccountMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FundMapper fundMapper;

    @Override
    public List<PettyCashActivityDto> getAllPettyCash() {
        // this method used for dashboard total count of petty cash
        return pettyCashActivityRepository.findAll().stream()
                .map(pettyCashMapper::toPettyCashActivityDto)
                .collect(Collectors.toList());
    }

    /*** Petty Cash  ****/

    @Override
    public Set<PettyCashActivityDto> getUnapprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role ->
                        role.getName().equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            List<PettyCashActivity> pettyCashActivityList = pettyCashActivityRepository.fillAllPettyCashDateDesc();

            return pettyCashActivityList
                    .stream()
                    .map(pettyCash -> {
                        // convert petty cash entity to dto using lambda expression
                        PettyCashActivityDto pettyCashDto = pettyCashMapper.toPettyCashActivityDto(pettyCash);

                        // fetch client accounts id
                        Set<Long> accountIds = pettyCashDto.getAccountIds();
                        // fetch accounts by their ids
                        List<ClientAccount> accounts = clientAccountRepository.findAllById(accountIds);

                        // get tha account details
                        Set<String> accountDetails = accounts.stream()
                                .map(account -> account.getAccountTitle() + " (" + account.getClientAccountType().displayClientAccountType() + ")")
                                .collect(Collectors.toSet());

                        // set petty cash dto client account details
                        pettyCashDto.setAccountDetails(accountDetails);

                        return pettyCashDto;
                    })
                    .collect(Collectors.toSet());
        } else  {
            // Employee User View
            List<PettyCashActivity> pettyCashActivityList = pettyCashActivityRepository.findUnapprovedPettyCashByReceivedByDateDesc(user);

            return pettyCashActivityList
                    .stream()
                    .map(pettyCash -> {
                        // convert petty cash entity to dto using lambda expression
                        PettyCashActivityDto pettyCashDto = pettyCashMapper.toPettyCashActivityDto(pettyCash);

                        // fetch client accounts id
                        Set<Long> accountIds = pettyCashDto.getAccountIds();
                        // fetch accounts by their ids
                        List<ClientAccount> accounts = clientAccountRepository.findAllById(accountIds);

                        // get tha account details
                        Set<String> accountDetails = accounts.stream()
                                .map(account -> account.getAccountTitle() + " (" + account.getClientAccountType().displayClientAccountType() + ")")
                                .collect(Collectors.toSet());

                        // set petty cash dto client account details
                        pettyCashDto.setAccountDetails(accountDetails);

                        return pettyCashDto;
                    })
                    .collect(Collectors.toSet());
        }
    }

    /****  My Activity ****/

    @Override
    public Set<PettyCashActivityDto> getApprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role -> role.getName()
                        .equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            List<PettyCashActivity> pettyCashActivityList = pettyCashActivityRepository.findAllApprovedPettyCashDateDesc();
            //return pettyCashActivityRepository.findAllApprovedPettyCashDateDesc();
            return pettyCashActivityList
                    .stream()
                    .map(pettyCash -> {
                        // convert petty cash entity to dto using lambda expression
                        PettyCashActivityDto pettyCashDto = pettyCashMapper.toPettyCashActivityDto(pettyCash);

                        // fetch client accounts id
                        Set<Long> accountIds = pettyCashDto.getAccountIds();
                        // fetch accounts by their ids
                        List<ClientAccount> accounts = clientAccountRepository.findAllById(accountIds);

                        // get tha account details
                        Set<String> accountDetails = accounts.stream()
                                .map(account -> account.getAccountTitle() + " (" + account.getClientAccountType().displayClientAccountType() + ")")
                                .collect(Collectors.toSet());

                        // set petty cash dto client account details
                        pettyCashDto.setAccountDetails(accountDetails);

                        return pettyCashDto;
                    })
                    .collect(Collectors.toSet());


        } else  {
            // Employee User View
            List<PettyCashActivity> pettyCashActivityList = pettyCashActivityRepository.findApprovedPettyCashByReceivedByDateDesc(user);
            return pettyCashActivityList
                    .stream()
                    .map(pettyCash -> {
                        // convert petty cash entity to dto using lambda expression
                        PettyCashActivityDto pettyCashDto = pettyCashMapper.toPettyCashActivityDto(pettyCash);

                        // fetch client accounts id
                        Set<Long> accountIds = pettyCashDto.getAccountIds();
                        // fetch accounts by their ids
                        List<ClientAccount> accounts = clientAccountRepository.findAllById(accountIds);

                        // get tha account details
                        Set<String> accountDetails = accounts.stream()
                                .map(account -> account.getAccountTitle() + " (" + account.getClientAccountType().displayClientAccountType() + ")")
                                .collect(Collectors.toSet());

                        // set petty cash dto client account details
                        pettyCashDto.setAccountDetails(accountDetails);

                        return pettyCashDto;
                    })
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public List<PettyCashActivity> getByClientAccountId(Long id) {
        return  pettyCashActivityRepository.findByClientAccountId(id);
    }

    @Override
    public List<PettyCashActivity> getAllPettyCashWithClientAccounts() {
        return pettyCashActivityRepository.findAll();
    }

    @Override
    public PettyCashActivityDto getPettyCashById(Long id) {
        PettyCashActivity pettyCash = pettyCashActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash ID not found!"));
        // Convert to Dto Accounts, Petty Cash, & Fund

        PettyCashActivityDto dto = pettyCashMapper.toPettyCashActivityDto(pettyCash);
/*
        Set<ClientAccountDto> accounts = pettyCash.getAccounts()
                .stream().
                map(clientAccountMapper::toClientAccountDto)
                .collect(Collectors.toSet());
        dto.setAccounts(accounts);*/

        User user = userRepository.findById(pettyCash.getReceivedBy().getUserId()).orElseThrow(null);
        dto.setReceivedById(user.getUserId());

        FundDto fund = fundMapper.toFundDto(pettyCash.getFund());
        dto.setFund(fund);

        //logger.info("Petty Cash: " + dto);
        return dto;
    }

    @Override
    public PettyCashActivity getPettyCashLiquidationById(Long id) {
        PettyCashActivity pettyCash = pettyCashActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Petty Cash not found"));
        Hibernate.initialize(pettyCash.getLiquidations());
        return pettyCash;
    }

    @Override
    public void savePettyCash(PettyCashActivityDto dto, String username) {
        PettyCashActivity pettyCash;

        if (dto.getPcActivityId() != null) {
            // UPDATE PETTY CASH
            pettyCash = pettyCashActivityRepository.findById(dto.getPcActivityId())
                    .orElse(new PettyCashActivity());

            //Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            if (dto.getFund() != null) {
                Fund fund = fundMapper.toFundEntity(dto.getFund());
                Fund fundId = fundRepository.findById(fund.getFundId()).orElse(null);
                pettyCash.setFund(fundId);
            }

            pettyCash.setPcActivityNo(dto.getPcActivityNo());
            pettyCash.setDate(dto.getDate());
            pettyCash.setActivityDescription(dto.getActivityDescription());
            pettyCash.setActivityCategory(dto.getActivityCategory());
            pettyCash.setSoaCategory(dto.getSoaCategory());

            pettyCash.setTotalAmount(dto.getTotalAmount());

            // Map AccountDto to Account by fetching Account entities from the database using the IDs
            Set<ClientAccount> clientAccounts = dto.getAccountIds()
                    .stream()
                    .map(clientAccountRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            pettyCash.setAccounts(clientAccounts);


            /*if (dto.getReceivedBy() != null) {
                User user = userMapper.toUserEntity(dto.getReceivedBy());
                pettyCash.setReceivedBy(user);
            }*/

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
            logger.info("Accounts save: " + pettyCash.getAccounts());

        } else {
            // CREATE NEW PETTY CASH

            pettyCash = pettyCashMapper.toPettyCashActivityEntity(dto);

            Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            pettyCash.setFund(manageFund);

            pettyCash.setPcActivityNo(dto.getPcActivityNo());
            pettyCash.setDate(dto.getDate());
            pettyCash.setActivityDescription(dto.getActivityDescription());
            pettyCash.setActivityCategory(dto.getActivityCategory());
            pettyCash.setSoaCategory(dto.getSoaCategory());

            // Map AccountDto to Account by fetching Account entities from the database using the IDs
            Set<ClientAccount> clientAccounts = dto.getAccountIds()
                    .stream()
                    .map(clientAccountRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            pettyCash.setAccounts(clientAccounts);

            pettyCash.setTotalAmount(dto.getTotalAmount());
            pettyCash.setApproved(dto.getApproved());

            /* USER Received By */
            if (dto.getReceivedById() != null) {
                User receivedBy = userRepository.findById(dto.getReceivedById()).orElseThrow(null);
                pettyCash.setReceivedBy(receivedBy);
            }

            User createdBy = userRepository.findByUsername(username);
            if (createdBy != null) {
                pettyCash.setApprovedBy(createdBy.getUserId()); // approved by
                pettyCash.setCreatedBy(createdBy.getUserId()); // created
                pettyCash.setUpdatedBy(createdBy.getUserId()); // updated
            }

            //logger.info("Successfully created new petty cash: " + pettyCash);
        }
        pettyCashActivityRepository.save(pettyCash);
        logger.info("Successfully created petty cash: " + pettyCash);
        logger.info("Added accounts successfully: " + pettyCash.getAccounts());
    }

    @Override
    public void saveAdminPettyCash(PettyCashActivityDto dto, String username) {
        PettyCashActivity pettyCash;

        if (dto.getPcActivityId() != null) {
            // UPDATE PETTY CASH
            pettyCash = pettyCashActivityRepository.findById(dto.getPcActivityId())
                    .orElse(new PettyCashActivity());

            //Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            if (dto.getFund() != null) {
                Fund fund = fundMapper.toFundEntity(dto.getFund());
                Fund fundId = fundRepository.findById(fund.getFundId()).orElse(null);
                pettyCash.setFund(fundId);
            }

            pettyCash.setPcActivityNo(dto.getPcActivityNo());
            pettyCash.setDate(dto.getDate());
            pettyCash.setActivityDescription(dto.getActivityDescription());
            pettyCash.setActivityCategory(dto.getActivityCategory());
            pettyCash.setSoaCategory(dto.getSoaCategory());

            pettyCash.setTotalAmount(dto.getTotalAmount());

            if (dto.getReceivedById() != null) {
                User receivedBy = userRepository.findById(dto.getReceivedById()).orElseThrow(null);
                pettyCash.setReceivedBy(receivedBy);
            }

            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                pettyCash.setUpdatedBy(updatedBy.getUserId());
            }

            //logger.info("Successfully updated petty cash cash admin works: " + pettyCash);

        } else {
            // CREATE NEW PETTY CASH

            pettyCash = new PettyCashActivity();

            Fund manageFund = fundRepository.getReferenceById(dto.getFund().getFundId());
            pettyCash.setFund(manageFund);

            pettyCash.setPcActivityNo(dto.getPcActivityNo());
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
            pettyCash.setApproved(false);
            pettyCash.setApprovedBy(null); // approved by

            User createdBy = userRepository.findByUsername(username);

            if (createdBy != null) {
                pettyCash.setReceivedBy(createdBy); // receiver
                pettyCash.setCreatedBy(createdBy.getUserId()); // created
                pettyCash.setUpdatedBy(createdBy.getUserId()); // updated
            }

            //logger.info("Successfully created new petty cash admin works: " + pettyCash);
        }
        pettyCashActivityRepository.save(pettyCash);
    }

    @Override
    public void save(PettyCashActivity pettyCash) {
        //logger.info("Saving... " + pettyCash);
        pettyCashActivityRepository.save(pettyCash);
    }

    @Override
    public void deletePettyCashRecordById(Long id) {
        pettyCashActivityRepository.deleteById(id);
    }
}
