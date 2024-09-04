package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.cash.LiquidationService;
import com.jamersc.springboot.financialhub.service.cash.PettyCashService;
import com.jamersc.springboot.financialhub.service.cash.PettyCashVoucherService;
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
    private LiquidationService liquidationService;

    @GetMapping("/petty-cash-voucher")
    public String pettyCashVoucherPage(Model model) {
        addPettyCashToModel(model);
        return  "cash/petty-cash";
    }

    @GetMapping("/petty-cash-form")
    public String pettyCashForm(Model model) {
        model.addAttribute("pettyCashDto", new PettyCashDto());
        return "cash/petty-cash-form";
    }

    @PostMapping("/petty-cash-form")
    public String processCreatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Please complete all required fields!");
            return "cash/petty-cash-form";
        } else {
            String createdBy = getSessionUsername();
            logger.info("Created petty cash voucher: " + pettyCashDto);
            pettyCashService.savePettyCashRecord(pettyCashDto, createdBy);
            return "redirect:/petty-cash/petty-cash-voucher";
        }
    }

    @GetMapping("/petty-cash-update-form/{id}")
    public String pettyCashUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCashDto pettyCashDto = pettyCashService.findPettyCashRecordById(id);
        if (pettyCashDto != null) {
            logger.info("Fetching petty cash form id: " + pettyCashDto.getId());
            model.addAttribute("pettyCashDto", pettyCashDto);
            return "cash/petty-cash-form";
        }
        return "redirect:/petty-cash/petty-cash-voucher";
    }

    @PostMapping("/petty-cash-update-form")
    public String processUpdatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            return "cash/petty-cash-form";
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
        PettyCashDto pettyCashDto = pettyCashService.findPettyCashRecordById(id);
        if (pettyCashDto == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream stream = pettyCashVoucherService.generatePettyCashVoucher(pettyCashDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + pettyCashDto.getPcvNumber() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(stream.readAllBytes());
    }

    private void addPettyCashToModel(Model model) {
        List<PettyCash> pettyCash = pettyCashService.getAllPettyCashRecord();
        model.addAttribute("pettyCash", pettyCash);
        logger.info("Get all petty cash record: " + pettyCash);
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
