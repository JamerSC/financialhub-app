package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.PettyCashService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/petty-cash")
public class PettyCashController {

    private static final Logger logger = LoggerFactory.getLogger(PettyCashController.class);

    @Autowired
    private PettyCashService pettyCashService;

    @GetMapping("/petty-cash-voucher")
    public String pettyCashVoucherPage(Model model) {
        List<PettyCash> pettyCash = pettyCashService.findALlPettyCashRecord();
        logger.info("Get all petty cash record\n" + pettyCash);
        model.addAttribute("pettyCash", pettyCash);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        if (result.hasErrors()) {
            logger.error("Please complete all required fields!");
            return "cash/petty-cash-form";
        } else {
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
            return "cash/petty-cash-update-form";
        }
        return "redirect:/petty-cash/petty-cash-voucher";
    }

    @PostMapping("/petty-cash-update-form")
    public String processUpdatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                             BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedBy = authentication.getName();
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            return "cash/petty-cash-update-form";
        } else {
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
}
