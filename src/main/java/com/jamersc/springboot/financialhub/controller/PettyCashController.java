package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
import com.jamersc.springboot.financialhub.service.petty_cash_activity.FundService;
import com.jamersc.springboot.financialhub.service.petty_cash_activity.PettyCashActivityService;
import com.jamersc.springboot.financialhub.service.petty_cash_activity.PettyCashVoucherService;
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
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/petty-cash")
public class PettyCashController {

    @Autowired
    private FundService fundService;
    @Autowired
    private PettyCashActivityService pettyCashService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PettyCashVoucherService pettyCashVoucherService;

    @GetMapping("/list-of-petty-cash")
    public String pettyCashVoucherPage(Model model, @RequestParam(defaultValue = "1") Long id,
                                       Principal principal) {

        User loggedInUser = userService.getByUsername(principal.getName());

        FundDto fund = fundService.getFundById(id); // fund id# 1
        //List<PettyCash> listOfPettyCash = pettyCashService.getAllPettyCashWithClientAccounts();
        //List<PettyCash> listOfPettyCash = pettyCashService.getPettyCashByUserRole(loggedInUser);
        List<PettyCashActivity> listOfPettyCashActivities = pettyCashService.getUnapprovedPettyCashByReceivedBy(loggedInUser);
        model.addAttribute("listOfPettyCashActivities", listOfPettyCashActivities);
        model.addAttribute("pettyCash", new PettyCashActivityDto());
        model.addAttribute("listOfAccounts", clientAccountService.getAllClientAccounts());
        model.addAttribute("fund", fund);
        List<User> internalUsers = userService.getAllUsers();
        model.addAttribute("internalUsers", internalUsers);
        return  "petty-cash/petty-cash";
    }

    @PostMapping("/{id}/add-petty-cash")
    public String pettyCashForm(@ModelAttribute("pettyCash") PettyCashActivityDto pettyCash,
                                @PathVariable(value = "id") Long id) {
        String createdBy = getSessionUsername();
        FundDto fund = fundService.getFundById(id);
        pettyCash.setFund(fund);
        pettyCashService.savePettyCash(pettyCash, createdBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/edit-petty-cash")
    @ResponseBody
    public PettyCashActivityDto findPettyCashRecordById(Long id) {
        return pettyCashService.getPettyCashById(id);
    }

    @PostMapping("/update-petty-cash")
    public String updatePettyCash(@ModelAttribute("pettyCash") PettyCashActivityDto pettyCash) {
        String updatedBy = getSessionUsername();
        pettyCashService.savePettyCash(pettyCash, updatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    // ADMIN SUPPORT PETTY CASH

    @PostMapping("/{id}/add-admin-petty-cash")
    public String addAdminPettyCash(@ModelAttribute("pettyCash") PettyCashActivityDto pettyCash,
                                         @PathVariable(value = "id") Long id) {
        String adminCreatedBy = getSessionUsername();
        FundDto fund = fundService.getFundById(id);
        pettyCash.setFund(fund);
        pettyCashService.saveAdminPettyCash(pettyCash, adminCreatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/edit-admin-petty-cash")
    @ResponseBody
    public PettyCashActivityDto findAdminPettyCashRecordById(Long id) {
        return pettyCashService.getPettyCashById(id);
    }

    @PostMapping("/update-admin-petty-cash")
    public String updateAdminPettyCash(@ModelAttribute("pettyCash") PettyCashActivityDto pettyCash) {
        String adminUpdatedBy = getSessionUsername();
        pettyCashService.saveAdminPettyCash(pettyCash, adminUpdatedBy);
        return "redirect:/petty-cash/list-of-petty-cash";
    }


    /* ------------------------- */

    @PostMapping("/petty-cash-form")
    public String processCreatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashActivityDto pettyCashActivityDto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "petty-cash/petty-cash-form";
        } else {
            String createdBy = getSessionUsername();
            pettyCashService.savePettyCash(pettyCashActivityDto, createdBy);
            return "redirect:/petty-cash/list-of-petty-cash";
        }
    }

    @GetMapping("/petty-cash-update-form/{id}")
    public String pettyCashUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCashActivityDto pettyCashActivityDto = pettyCashService.getPettyCashById(id);
        if (pettyCashActivityDto != null) {
            model.addAttribute("pettyCashDto", pettyCashActivityDto);
            return "petty-cash/petty-cash-update-form";
        }
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @PostMapping("/petty-cash-update-form")
    public String processUpdatePettyCashForm(@Valid @ModelAttribute("pettyCashDto") PettyCashActivityDto pettyCashActivityDto,
                                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "petty-cash/petty-cash-update-form";
        } else {
            String updatedBy = getSessionUsername();
            pettyCashService.savePettyCash(pettyCashActivityDto, updatedBy);
            return "redirect:/petty-cash/list-of-petty-cash";
        }
    }

    @GetMapping("/delete-petty-cash-record/{id}")
    public String deletePettyCashRecord(@PathVariable(value = "id") Long id) {
        pettyCashService.deletePettyCashRecordById(id);
        return "redirect:/petty-cash/list-of-petty-cash";
    }

    @GetMapping("/generate-petty-cash-voucher/{id}")
    public ResponseEntity<byte[]> generatePettyCashVoucher(@PathVariable(value = "id") Long id) {
        PettyCashActivityDto pettyCashActivityDto = pettyCashService.getPettyCashById(id);
        if (pettyCashActivityDto == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream stream = pettyCashVoucherService.generatePettyCashVoucher(pettyCashActivityDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + pettyCashActivityDto.getPcActivityNo() + ".pdf");

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
