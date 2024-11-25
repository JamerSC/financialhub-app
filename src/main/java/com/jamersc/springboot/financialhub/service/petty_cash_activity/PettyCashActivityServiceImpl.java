package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
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

    //private static final Logger logger = LoggerFactory.getLogger(PettyCashActivityServiceImpl.class);
    @Autowired
    private PettyCashActivityRepository pettyCashActivityRepository;
    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FundRepository fundRepository;

    @Override
    public List<PettyCashActivityDto> getAllPettyCash() {
        //logger.info("Get all petty cash records.");
        return pettyCashActivityRepository.findAll().stream()
                .map(PettyCashMapper::toPettyCashDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PettyCashActivity> getUnapprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role -> role.getName()
                        .equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            return pettyCashActivityRepository.fillAllPettyCashDateDesc();
        } else  {
            //
            return pettyCashActivityRepository.findUnapprovedPettyCashByReceivedByDateDesc(user);
        }
    }

    @Override
    public List<PettyCashActivity> getApprovedPettyCashByReceivedBy(User user) {
        if (user.getRoles()
                .stream()
                .anyMatch(role -> role.getName()
                        .equals("ROLE_MANAGER") || role.getName().equals("ROLE_ADMIN"))) {
            // Admin & Manager View Petty Cash
            return pettyCashActivityRepository.fillAllPettyCashDateDesc();
        } else  {
            //
            return pettyCashActivityRepository.findApprovedPettyCashByReceivedByDateDesc(user);
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

        PettyCashActivityDto dto = PettyCashMapper.toPettyCashDto(pettyCash);

        Set<ClientAccountDto> accounts = pettyCash.getAccounts()
                .stream().
                map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toSet());
        dto.setAccounts(accounts);

        UserDto user = UserMapper.toUserDto(pettyCash.getReceivedBy());
        dto.setReceivedBy(user);

        FundDto fund = FundMapper.toFundDto(pettyCash.getFund());
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
                Fund fund = FundMapper.toFundEntity(dto.getFund());
                Fund fundId = fundRepository.findById(fund.getFundId()).orElse(null);
                pettyCash.setFund(fundId);
            }

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

            //logger.info("Successfully updated petty cash: " + pettyCash);

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

            //logger.info("Successfully created new petty cash: " + pettyCash);
        }
        pettyCashActivityRepository.save(pettyCash);
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
                Fund fund = FundMapper.toFundEntity(dto.getFund());
                Fund fundId = fundRepository.findById(fund.getFundId()).orElse(null);
                pettyCash.setFund(fundId);
            }

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

            if (dto.getReceivedBy() != null) {
                pettyCash.setReceivedBy(UserMapper.toUserEntity(dto.getReceivedBy()));
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
