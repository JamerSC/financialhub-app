package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.CaseAccountDto;
import com.jamersc.springboot.financialhub.mapper.CaseAccountMapper;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.CaseAccount;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CaseAccountRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import com.jamersc.springboot.financialhub.service.bank.BankAccountServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CaseServiceImpl implements CaseService{

    private static final Logger logger = LoggerFactory.getLogger(CaseServiceImpl.class);
    @Autowired
    private CaseAccountRepository caseAccountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CaseAccount> getAllCases() {
        return caseAccountRepository.findAll();
    }

    @Override
    public CaseAccountDto findCaseById(Long id) {
        CaseAccount caseAccount = caseAccountRepository.findById(id).orElse(null);
        if (caseAccount != null) {
            CaseAccountDto caseAccountDto = CaseAccountMapper.toCaseAccountDto(caseAccount);
            logger.info("Find case account details" + caseAccountDto);
            return caseAccountDto;
        }
        throw new RuntimeException("Case Account ID not found!");
        //return caseAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Case ID not found."));
    }

    @Override
    public void save(CaseAccountDto caseAccountDto, String username) {
        CaseAccount caseAccount;
        caseAccount = new CaseAccount();
        caseAccount.setCaseType(caseAccountDto.getCaseType());
        caseAccount.setStatus(caseAccountDto.getStatus());
        caseAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(caseAccountDto.getClientAccount()));
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            caseAccount.setCreatedBy(createdBy.getId());
            caseAccount.setUpdatedBy(createdBy.getId());
        }
        caseAccountRepository.save(caseAccount);
    }

    @Override
    public void deleteCaseById(Long id) {
        caseAccountRepository.deleteById(id);
    }
}
