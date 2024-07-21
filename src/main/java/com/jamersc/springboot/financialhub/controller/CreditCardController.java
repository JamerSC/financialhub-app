package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/credit-card-voucher")
    public String creditCardVoucherPage(Model model) {
        List<CreditCard> creditCards = creditCardService.findAllCreditCardRecord();
        model.addAttribute("creditCards", creditCards);
        return  "credit-card/credit-card";
    }
}
