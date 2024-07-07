package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.entity.CreditCard;

import java.util.List;

public interface CreditCardService {

    List<CreditCard> findAllCreditCardRecord();

    CreditCardDto findCreditCardRecordById(Integer id);

    void saveCreditCardRecord(CreditCardDto creditCardDto);

    void deleteCreditCardRecordById(Integer id);

}
