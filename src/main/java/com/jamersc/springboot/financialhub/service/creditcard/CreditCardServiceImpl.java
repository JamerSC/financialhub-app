package com.jamersc.springboot.financialhub.service.creditcard;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CreditCardRepo;
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
    private CreditCardRepo creditCardRepo;

    @Autowired
    private UserService userService;

    @Override
    public List<CreditCard> findAllCreditCardRecord() {
        return creditCardRepo.findAll();
    }

    @Override
    public CreditCardDto findCreditCardRecordById(Long id) {
        CreditCard creditCard = creditCardRepo.findById(id).orElse(null);
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
            creditCard = creditCardRepo.findById(creditCardDto.getId()).orElse(new CreditCard());
            User updatedBy = userService.findByUsername(username);
            if (updatedBy != null) {
                creditCard.setUpdatedBy(Math.toIntExact(updatedBy.getId()));
            } else {
                creditCard.setUpdatedBy(1);
            }
        } else {
            creditCard = new CreditCard();
            User createdBy = userService.findByUsername(username);
            if (createdBy != null) {
                creditCard.setCreatedBy(Math.toIntExact(createdBy.getId()));
                creditCard.setUpdatedBy(Math.toIntExact(createdBy.getId()));
            } else {
                creditCard.setCreatedBy(1);
                creditCard.setUpdatedBy(1);
            }
        }
        BeanUtils.copyProperties(creditCardDto, creditCard);
        creditCardRepo.save(creditCard);
    }

    @Override
    public void deleteCreditCardRecordById(Long id) {
        creditCardRepo.deleteById(id);
    }
}
