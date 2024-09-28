package com.jamersc.springboot.financialhub.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/retainers")
public class RetainerController {

    @GetMapping("/list-of-retainers")
    public String listOfRetainers(Model model) {
        return "retainer/retainer";
    }
}
