package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.CreditCardDto;
import com.jamersc.springboot.financialhub.model.CreditCard;
import com.jamersc.springboot.financialhub.service.creditcard.CreditCardService;
import com.jamersc.springboot.financialhub.service.creditcard.CreditCardVoucherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/credit-card")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private CreditCardVoucherService creditCardVoucherService;

    @GetMapping("/credit-card-voucher")
    public String creditCardVoucherPage(Model model) {
        List<CreditCard> creditCards = creditCardService.findAllCreditCardRecord();
        model.addAttribute("creditCards", creditCards);
        return  "credit-card/credit-card";
    }

    @GetMapping("/credit-card-form")
    public String creditCardForm(Model model) {
        model.addAttribute("creditCardDto", new CreditCardDto());
        return "credit-card/credit-card-form";
    }

    @PostMapping("/credit-card-form")
    public String processCreditCardForm(@Valid @ModelAttribute("creditCardDto") CreditCardDto creditCardDto,
                                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "credit-card/credit-card-form";
        } else  {
            String createdBy = getSessionUsername();
            creditCardService.saveCreditCardRecord(creditCardDto, createdBy);
            return "redirect:/credit-card/credit-card-voucher";
        }
    }

    @GetMapping("/credit-card-update-form/{id}")
    public String creditCardUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        CreditCardDto creditCardDto = creditCardService.findCreditCardRecordById(id);
        if (creditCardDto != null) {
            model.addAttribute("creditCardDto", creditCardDto);
            return "credit-card/credit-card-update-form";
        }
        return "redirect:/credit-card/credit-card-voucher";
    }

    @PostMapping("/credit-card-update-form")
    public String processCreditCardUpdateForm(@Valid @ModelAttribute("creditCardDto") CreditCardDto creditCardDto,
                                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "credit-card/credit-card-update-form";
        } else {
            String updatedBy = getSessionUsername();
            creditCardService.saveCreditCardRecord(creditCardDto, updatedBy);
            return "redirect:/credit-card/credit-card-voucher";
        }
    }

    @GetMapping("/delete-credit-card-record/{id}")
    public String deleteCreditCardRecord(@PathVariable(value = "id") Long id, Model model) {
        creditCardService.deleteCreditCardRecordById(id);
        return "redirect:/credit-card/credit-card-voucher";
    }

    @GetMapping("/generate-credit-card-voucher/{id}")
    public ResponseEntity<byte[]> generateCreditCardVoucher(@PathVariable(value = "id") Long id) {
        CreditCardDto creditCardDto = creditCardService.findCreditCardRecordById(id);
        if (creditCardDto == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream stream = creditCardVoucherService.generateCreditCardVoucher(creditCardDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + creditCardDto.getCcvNumber() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(stream.readAllBytes());
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
