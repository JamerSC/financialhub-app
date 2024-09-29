package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.CaseType;
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
        model.addAttribute("caseType", CaseType.values());
        return "case/case";
    }

    @GetMapping("/case-summary")
    public String caseInformation(Model model) {
        return "case/case-summary";
    }
}
