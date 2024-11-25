package com.jamersc.springboot.financialhub.service.creditcard;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CreditCardRepository;
import com.jamersc.springboot.financialhub.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserService userService;

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
    public void saveCreditCardRecord(CreditCardDto creditCardDto, String username) {
        CreditCard creditCard;
        if (creditCardDto.getId() != null) {
            creditCard = creditCardRepository.findById(creditCardDto.getId()).orElse(new CreditCard());
            User updatedBy = userService.getByUsername(username);
            if (updatedBy != null) {
                creditCard.setUpdatedBy(updatedBy.getUserId());
            } else {
                creditCard.setUpdatedBy(1L);
            }
        } else {
            creditCard = new CreditCard();
            User createdBy = userService.getByUsername(username);
            if (createdBy != null) {
                creditCard.setCreatedBy(createdBy.getUserId());
                creditCard.setUpdatedBy(createdBy.getUserId());
            } else {
                creditCard.setCreatedBy(1L);
                creditCard.setUpdatedBy(1L);
            }
        }
        BeanUtils.copyProperties(creditCardDto, creditCard);
        creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCardRecordById(Long id) {
        creditCardRepository.deleteById(id);
    }
}
