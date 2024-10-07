package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.model.CaseAccount;

import java.util.List;

public interface CaseService {

    List<CaseAccount> getAllCases();

    CaseAccount findCaseById(Long id);

    void save(CaseAccount newCase, String username);

    void deleteCaseById(Long id);
}
