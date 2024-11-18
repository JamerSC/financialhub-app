package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import com.jamersc.springboot.financialhub.service.pettycash.FundService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashVoucherService;
import com.jamersc.springboot.financialhub.service.user.UserService;
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
    private FundService fundService;
    @Autowired
    private PettyCashService pettyCashService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    private PettyCashVoucherService pettyCashVoucherService;

    @GetMapping("/list-of-petty-cash")
    public String pettyCashVoucherPage(Model model,  @RequestParam(defaultValue = "1") Long id) {
        FundDto fund = fundService.getFundById(id); // fund id# 1
        List<PettyCash> listOfPettyCash = pettyCashService.getAllPettyCashWithClientAccounts();
        model.addAttribute("listOfPettyCash", listOfPettyCash);
        model.addAttribute("pettyCash", new PettyCashDto());
        model.addAttribute("listOfAccounts", clientAccountService.getAllClientAccounts());
        model.addAttribute("fund", fund);
        List<User> internalUsers = userService.getAllUsers();
        model.addAttribute("internalUsers", internalUsers);
        return  "petty-cash/petty-cash";
    }

    @PostMapping("/{id}/add-petty-cash")
    public String pettyCashForm(@ModelAttribute("pettyCash") PettyCashDto pettyCash,
                                @PathVariable(value = "id") Long id) {
        String createdBy = getSessionUsername();
        FundDto fund = fundService.getFundById(id);
        pettyCash.setFund(fund);
        pettyCashService.savePettyCash(pettyCash, createdBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/edit-petty-cash")
    @ResponseBody
    public PettyCashDto findPettyCashRecordById(Long id) {
        return pettyCashService.getPettyCashById(id);
    }

    @PostMapping("/update-petty-cash")
    public String updatePettyCash(@ModelAttribute("pettyCash") PettyCashDto pettyCash) {
        String updatedBy = getSessionUsername();
        pettyCashService.savePettyCash(pettyCash, updatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    // ADMIN SUPPORT PETTY CASH

    @PostMapping("/{id}/add-admin-petty-cash")
    public String addAdminPettyCash(@ModelAttribute("pettyCash") PettyCashDto pettyCash,
                                         @PathVariable(value = "id") Long id) {
        String adminCreatedBy = getSessionUsername();
        FundDto fund = fundService.getFundById(id);
        pettyCash.setFund(fund);
        pettyCashService.saveAdminPettyCash(pettyCash, adminCreatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/edit-admin-petty-cash")
    @ResponseBody
    public PettyCashDto findAdminPettyCashRecordById(Long id) {
        return pettyCashService.getPettyCashById(id);
    }

    @PostMapping("/update-admin-petty-cash")
    public String updateAdminPettyCash(@ModelAttribute("pettyCash") PettyCashDto pettyCash) {
        String adminUpdatedBy = getSessionUsername();
        pettyCashService.saveAdminPettyCash(pettyCash, adminUpdatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }


    /* ------------------------- */

    @PostMapping("/petty-cash-form")
    public String processCreatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Please complete all required fields!");
            return "petty-cash/petty-cash-form";
        } else {
            String createdBy = getSessionUsername();
            logger.info("Created petty cash voucher: " + pettyCashDto);
            pettyCashService.savePettyCash(pettyCashDto, createdBy);
            return "redirect:/petty-cash/list-of-petty-cash";
        }
    }

    @GetMapping("/petty-cash-update-form/{id}")
    public String pettyCashUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCashDto pettyCashDto = pettyCashService.getPettyCashById(id);
        if (pettyCashDto != null) {
            logger.info("Fetching petty cash form id: " + pettyCashDto.getPettyCashId());
            model.addAttribute("pettyCashDto", pettyCashDto);
            return "petty-cash/petty-cash-update-form";
        }
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @PostMapping("/petty-cash-update-form")
    public String processUpdatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            return "petty-cash/petty-cash-update-form";
        } else {
            String updatedBy = getSessionUsername();
            pettyCashService.savePettyCash(pettyCashDto, updatedBy);
            logger.info("Updated petty cash voucher!\n" + pettyCashDto);
            return "redirect:/petty-cash/list-of-petty-cash";
        }
    }

    @GetMapping("/delete-petty-cash-record/{id}")
    public String deletePettyCashRecord(@PathVariable(value = "id") Long id) {
        logger.info("Process deleting petty cash form id: " + id);
        pettyCashService.deletePettyCashRecordById(id);
        logger.info("Deleted petty cash form id: " + id);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/generate-petty-cash-voucher/{id}")
    public ResponseEntity<byte[]> generatePettyCashVoucher(@PathVariable(value = "id") Long id) {
        PettyCashDto pettyCashDto = pettyCashService.getPettyCashById(id);
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
