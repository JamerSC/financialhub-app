package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.model.CaseAccount;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CaseRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CaseServiceImpl implements CaseService{

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CaseAccount> getAllCases() {
        return caseRepository.findAll();
    }

    @Override
    public CaseAccount findCaseById(Long id) {
        return caseRepository.findById(id).orElseThrow(() -> new RuntimeException("Case ID not found."));
    }

    @Override
    public void save(CaseAccount newCase, String username) {
        CaseAccount tempCase;
        tempCase = newCase;
        tempCase.setCaseType(newCase.getCaseType());
        tempCase.setStatus(newCase.getStatus());
        //tempCase.setClient(newCase.getClient());
        tempCase.setClientAccount(newCase.getClientAccount());
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            tempCase.setCreatedBy(createdBy.getId());
            tempCase.setUpdatedBy(createdBy.getId());
        }
        caseRepository.save(tempCase);
    }

    @Override
    public void deleteCaseById(Long id) {
        caseRepository.deleteById(id);
    }
}
