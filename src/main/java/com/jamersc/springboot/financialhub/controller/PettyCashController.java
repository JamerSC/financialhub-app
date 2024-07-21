package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.PettyCashService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/petty-cash")
public class PettyCashController {

    @Autowired
    private PettyCashService pettyCashService;

    @GetMapping("/petty-cash-voucher")
    public String pettyCashVoucherPage(Model model) {
        List<PettyCash> pettyCash = pettyCashService.findALlPettyCashRecord();
        model.addAttribute("pettyCash", pettyCash);
        return  "cash/petty-cash";
    }

    @GetMapping("/petty-cash-form")
    public String pettyCashForm(Model model) {
        model.addAttribute("pettyCashDto", new PettyCashDto());
        return "cash/petty-cash-form";
    }

    @PostMapping("/petty-cash-form")
    public String processPettyCash(@Valid @ModelAttribute("pettyCashDto") PettyCashDto pettyCashDto,
                                   BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionUsername = authentication.getName();
        if (result.hasErrors()) {
            return "cash/petty-cash-form";
        } else {
            pettyCashService.savePettyCashRecord(pettyCashDto, sessionUsername);
            return "redirect:/petty-cash/petty-cash-voucher";
        }
    }
}
