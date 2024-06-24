package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.repository.CheckRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreditCardServiceImpli {
    // implements CreditCardService

/*
    private CheckRepository checkRepository;

    @Autowired
    public CreditCardServiceImpli(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Override
    public List<CreditCard> findAllCreditCardRecord() {
        return null;
    }

    @Override
    public CreditCardDto findCreditCardRecordById(Integer id) {
        return null;
    }

    @Override
    public void saveCreditCardRecord(CreditCardDto creditCardDto) {

    }

    @Override
    public void deleteCreditCardRecordById(Integer id) {

    }*/
}
