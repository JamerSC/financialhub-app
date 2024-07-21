package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.repository.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CreditCardServiceImpli implements CreditCardService{

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> findAllCreditCardRecord() {
        return creditCardRepository.findAll();
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

    }
}
