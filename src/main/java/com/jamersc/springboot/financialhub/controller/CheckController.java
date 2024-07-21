package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.service.CheckService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @GetMapping("/check-voucher")
    public String checkVoucherPage(Model model) {
        List<Check> checks = checkService.getAllCheckRecord();
        model.addAttribute("checks", checks);
        return  "check/check";
    }
}
