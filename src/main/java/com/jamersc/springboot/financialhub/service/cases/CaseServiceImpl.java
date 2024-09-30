package com.jamersc.springboot.financialhub.service.cases;

import com.jamersc.springboot.financialhub.model.CaseType;
import com.jamersc.springboot.financialhub.model.Cases;
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
    public List<Cases> getAllCases() {
        return caseRepository.findAll();
    }

    @Override
    public Cases findCaseById(Long id) {
        return caseRepository.findById(id).orElseThrow(() -> new RuntimeException("Case ID not found."));
    }

    @Override
    public void save(Cases newCase, String username) {
        Cases tempCase;
        tempCase = newCase;
        tempCase.setCaseType(newCase.getCaseType());
        tempCase.setStatus(newCase.getStatus());
        tempCase.setClient(newCase.getClient());
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            tempCase.setCreatedBy(Math.toIntExact(createdBy.getId()));
            tempCase.setUpdatedBy(Math.toIntExact(createdBy.getId()));
        }
        caseRepository.save(tempCase);
    }

    @Override
    public void deleteCaseById(Long id) {
        caseRepository.deleteById(id);
    }
}
