package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.pettycash.FundService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashVoucherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/petty-cash")
public class PettyCashController {

    private static final Logger logger = LoggerFactory.getLogger(PettyCashController.class);

    @Autowired
    private PettyCashService pettyCashService;

    @Autowired
    private PettyCashVoucherService pettyCashVoucherService;

    @Autowired
    private FundService fundService;

    @GetMapping("/petty-cash-voucher")
    public String pettyCashVoucherPage(Model model,  @RequestParam(defaultValue = "1") Long id) {
        Fund fund = fundService.getFundById(id); // fund id# 1
        List<PettyCashDto> pettyCash = pettyCashService.getAllPettyCashRecord();
        model.addAttribute("pettyCash", pettyCash);
        model.addAttribute("fund", fund);
        return  "petty-cash/petty-cash";
    }

    @GetMapping("/{id}/petty-cash-form")
    public String pettyCashForm(@PathVariable(value = "id") Long id, Model model) {
        Fund fund = fundService.getFundById(id);
        PettyCashDto pettyCashDto = new PettyCashDto();
        //pettyCashDto.setFund(fund);
        model.addAttribute("pettyCashDto",pettyCashDto);
        model.addAttribute("fund", fund);
        return "petty-cash/petty-cash-form";
    }

    @PostMapping("/petty-cash-form")
    public String processCreatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Please complete all required fields!");
            return "petty-cash/petty-cash-form";
        } else {
            String createdBy = getSessionUsername();
            logger.info("Created petty cash voucher: " + pettyCashDto);
            pettyCashService.savePettyCashRecord(pettyCashDto, createdBy);
            return "redirect:/petty-cash/petty-cash-voucher";
        }
    }

    @GetMapping("/petty-cash-update-form/{id}")
    public String pettyCashUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCashDto pettyCashDto = pettyCashService.findPettyCashById(id);
        if (pettyCashDto != null) {
            logger.info("Fetching petty cash form id: " + pettyCashDto.getPettyCashId());
            model.addAttribute("pettyCashDto", pettyCashDto);
            return "petty-cash/petty-cash-update-form";
        }
        return "redirect:/petty-cash/petty-cash-voucher";
    }

    @PostMapping("/petty-cash-update-form")
    public String processUpdatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            return "petty-cash/petty-cash-update-form";
        } else {
            String updatedBy = getSessionUsername();
            pettyCashService.savePettyCashRecord(pettyCashDto, updatedBy);
            logger.info("Updated petty cash voucher!\n" + pettyCashDto);
            return "redirect:/petty-cash/petty-cash-voucher";
        }
    }

    @GetMapping("/delete-petty-cash-record/{id}")
    public String deletePettyCashRecord(@PathVariable(value = "id") Long id) {
        logger.info("Process deleting petty cash form id: " + id);
        pettyCashService.deletePettyCashRecordById(id);
        logger.info("Deleted petty cash form id: " + id);
        return "redirect:/petty-cash/petty-cash-voucher";
    }

    @GetMapping("/generate-petty-cash-voucher/{id}")
    public ResponseEntity<byte[]> generatePettyCashVoucher(@PathVariable(value = "id") Long id) {
        PettyCashDto pettyCashDto = pettyCashService.findPettyCashById(id);
        if (pettyCashDto == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream stream = pettyCashVoucherService.generatePettyCashVoucher(pettyCashDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + pettyCashDto.getVoucherNo() + ".pdf");

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
