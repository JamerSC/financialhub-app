package com.jamersc.springboot.financialhub.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/cases")
public class CaseController {

    @GetMapping("/list-of-cases")
    public String listOfCases(Model model) {
        //model.addAttribute("cases", "List of cases");
        return "case/case";
    }
}
