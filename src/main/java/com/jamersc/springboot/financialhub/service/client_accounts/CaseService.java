package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.CaseAccountDto;
import com.jamersc.springboot.financialhub.model.CaseAccount;

import java.util.List;

public interface CaseService {

    List<CaseAccount> getAllCases();

    CaseAccountDto findCaseById(Long id);

    void save(CaseAccountDto caseAccountDto, String username);

    void deleteCaseById(Long id);
}
