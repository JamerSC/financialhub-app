package com.jamersc.springboot.financialhub.service.creditcard;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;

import java.util.List;

public interface CreditCardService {

    List<CreditCard> findAllCreditCardRecord();
    CreditCardDto findCreditCardRecordById(Long id);
    void saveCreditCardRecord(CreditCardDto creditCardDto, String username);
    void deleteCreditCardRecordById(Long id);
}
