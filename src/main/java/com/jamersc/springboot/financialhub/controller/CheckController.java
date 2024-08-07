package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.CheckDto;
import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.service.CheckService;
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
@RequestMapping("/check")
public class CheckController {

    private static final Logger logger = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    private CheckService checkService;

    @GetMapping("/check-voucher")
    public String checkVoucherPage(Model model) {
        List<Check> checks = checkService.getAllCheckRecord();
        model.addAttribute("checks", checks);
        return  "check/check";
    }

    @GetMapping("/check-form")
    public String checkForm(Model model) {
        model.addAttribute("checkDto", new CheckDto());
        return "check/check-form";
    }

    @PostMapping("/check-form")
    public String processCreateCheckForm(@Valid @ModelAttribute("checkDto") CheckDto checkDto,
                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Please complete the required fields!");
            return "check/check-form";
        } else {
            String createdBy = getSessionUsername();
            checkService.saveCheckRecord(checkDto, createdBy);
            logger.info("Created new check voucher: " + checkDto);
            return "redirect:/check/check-voucher";
        }
    }

    @GetMapping("/check-update-form/{id}")
    public String checkUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        CheckDto checkDto = checkService.findCheckRecordById(id);
        if (checkDto != null) {
            model.addAttribute("checkDto", checkDto);
            return "check/check-update-form";
        }
        return "redirect:/check/check-voucher";
    }

    @PostMapping("/check-update-form")
    public String processCheckUpdateForm(@Valid @ModelAttribute("checkDto") CheckDto checkDto,
                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warn("Please complete the required fields!");
            return "check/check-update-form";
        } else {
            String updatedBy = getSessionUsername();
            checkService.updateCheckRecord(checkDto, updatedBy);
            logger.info("Updated successfully! " + checkDto);
            return "redirect:/check/check-voucher";
        }
    }

    @GetMapping("/delete-check-record/{id}")
    public String deleteCheckRecord(@PathVariable(value = "id") Long id) {
        logger.info("Preparing to delete check record no.: " + id);
        checkService.deleteCheckRecordById(id);
        logger.info("Deleted successfully! Check record no.: " + id);
        return "redirect:/check/check-voucher";
    }

    private String getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
