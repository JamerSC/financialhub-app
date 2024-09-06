package com.jamersc.springboot.financialhub.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/deposit")
public class DepositController {

    @GetMapping("/deposit-cash-check")
    public String depositPage(Model model) {
        model.addAttribute("message", "Add Deposit Details!");
        return "deposit/deposit";
    }
}
