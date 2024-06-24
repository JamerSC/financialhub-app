package com.jamersc.springboot.financialhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CheckController {

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello, World!");
        return  "index";
    }
}
