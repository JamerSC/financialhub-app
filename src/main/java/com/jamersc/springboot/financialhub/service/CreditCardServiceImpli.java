package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.repository.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    public CreditCardDto findCreditCardRecordById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
        if (creditCard != null) {
            CreditCardDto creditCardDto = new CreditCardDto();
            BeanUtils.copyProperties(creditCard, creditCardDto);
            return creditCardDto;
        }
        return null;
    }

    @Override
    public void saveCreditCardRecord(CreditCardDto creditCardDto) {

    }

    @Override
    public void deleteCreditCardRecordById(Long id) {
        creditCardRepository.deleteById(id);
    }
}
