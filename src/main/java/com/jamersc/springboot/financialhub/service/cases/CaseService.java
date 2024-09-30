package com.jamersc.springboot.financialhub.service.cases;

import com.jamersc.springboot.financialhub.model.Cases;

import java.util.List;

public interface CaseService {

    List<Cases> getAllCases();

    Cases findCaseById(Long id);

    void save(Cases newCase, String username);

    void deleteCaseById(Long id);
}
